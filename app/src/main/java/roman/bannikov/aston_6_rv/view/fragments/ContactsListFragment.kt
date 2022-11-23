package roman.bannikov.aston_6_rv.view.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import roman.bannikov.aston_6_rv.R
import roman.bannikov.aston_6_rv.adapters.ContactAdapter
import roman.bannikov.aston_6_rv.databinding.FragmentContactsListBinding
import roman.bannikov.aston_6_rv.model.navigator
import roman.bannikov.aston_6_rv.viewmodels.ContactsListViewModel
import roman.bannikov.aston_6_rv.viewmodels.ContactsListViewModelFactory


class ContactsListFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentContactsListBinding? = null
    private val binding: FragmentContactsListBinding get() = _binding!!
    private lateinit var vModel: ContactsListViewModel
    private var contactAdapter: ContactAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onBackPress()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactsListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true) //fixme или фих с ним?

        initVM()
        initFAB()
        initRV()
        compareContacts()
        return binding.root
    }

    private fun initVM() {
        vModel = ViewModelProvider(
            this,
            ContactsListViewModelFactory(requireContext())
        )[ContactsListViewModel::class.java]
        vModel.checkRoom()
        vModel.getContactsList()
        contactAdapter = ContactAdapter(listener = vModel)
    }

    private fun initFAB() {
        binding.fabAddNewContact.setOnClickListener {
            navigator().launchContactDetailsFragment(
                contact = null
            )
        }
    }

    private fun initRV() {
        binding.rvContactsList.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
            layoutManager = linearLayoutManager
            hasFixedSize()
            adapter = this@ContactsListFragment.contactAdapter
        }
        val divider = DividerItemDecoration(requireContext(), RecyclerView.VERTICAL)
        ResourcesCompat.getDrawable(resources, R.drawable.divider, null)
            ?.let { drawable -> divider.setDrawable(drawable) }
        binding.rvContactsList.addItemDecoration(divider)
    }


    private fun compareContacts() {
        vModel.listContacts.observe(viewLifecycleOwner, Observer { it ->
            val sortedList = it?.sortedWith(
                compareBy(
                    { it.id },
                    { it.name },
                    { it.lastname },
                    { it.phoneNumber })
            )
            contactAdapter!!.submitList(sortedList)
        })

        showAlertDialog()

        vModel.getTransferContact().observe(viewLifecycleOwner, Observer {
            navigator().launchContactDetailsFragment(
                contact = it
            )
        })
    }


    private fun showAlertDialog() {
        vModel.alertDialogLiveData.observe(viewLifecycleOwner, Observer {
            AlertDialog.Builder(requireContext())
                .setMessage(getString(R.string.alert_delete_contact))
                .setPositiveButton(getString(R.string.alert_positive_button_text)) { _, _ ->
                    vModel.deleteContact(
                        it
                    )
                }
                .setNegativeButton(getString(R.string.alert_negative_button_text)) { _, _ -> }
                .show()
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
        val search = menu.findItem(R.id.menu_search)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = false
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(input: String?): Boolean {
        if (input != null) {
            searchDatabase(request = input)
        }
        return true
    }

    override fun onQueryTextChange(input: String?): Boolean {
        if (input != null) {
            searchDatabase(request = input)
        }
        return true
    }

    private fun searchDatabase(request: String) { //fixme query?
        val searchRequest = "%$request%"
        vModel.searchContact(searchRequest = searchRequest)
    }

    private fun onBackPress() {
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        requireActivity().finish()
                    }
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}