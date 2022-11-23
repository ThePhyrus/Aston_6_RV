package roman.bannikov.aston_6_rv.view.fragments

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import roman.bannikov.aston_6_rv.databinding.FragmentContactDetailsBinding
import roman.bannikov.aston_6_rv.model.Contact
import roman.bannikov.aston_6_rv.model.navigator
import roman.bannikov.aston_6_rv.viewmodels.ContactDetailsViewModel
import roman.bannikov.aston_6_rv.viewmodels.ContactDetailsViewModelFactory


class ContactDetailsFragment : Fragment() {

    private var _binding: FragmentContactDetailsBinding? = null
    private val binding: FragmentContactDetailsBinding get() = _binding!!
    private var vm: ContactDetailsViewModel? = null
    private var contact: Contact? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
        requireActivity()
            .onBackPressedDispatcher
            .addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        navigator().launchContactsListFragment()
                    }
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactDetailsBinding.inflate(inflater, container, false)

        vm = ViewModelProvider(
            this,
            ContactDetailsViewModelFactory(requireContext())
        )[ContactDetailsViewModel::class.java]

        saveOrUpdateContact()
        init()
        return binding.root
    }

    private fun init() {
        arguments?.let {
            contact = it.getParcelable(KEY_CONTACT)
        }
        binding.tvName.text = contact?.name
        binding.tvLastname.text = contact?.lastname
        binding.tvPhoneNumber.text = contact?.phoneNumber

    }

    private fun saveOrUpdateContact() {
        binding.btnSave.setOnClickListener {

            if (contact == null) {

                vm!!.save(
                    firstName = binding.etEditName.text.toString(),
                    lastName = binding.etEditLastname.text.toString(),
                    phone = binding.etEditPhoneNumber.text.toString(),
                    id = null
                )
                navigator().launchContactsListFragment()
            } else {
                val firstName = binding.etEditName.text.toString().ifEmpty { contact!!.name }
                val lastName =
                    binding.etEditLastname.text.toString().ifEmpty { contact!!.lastname }
                val phoneNumber =
                    binding.etEditPhoneNumber.text.toString().ifEmpty { contact!!.phoneNumber }

                vm!!.update(
                    firstName = firstName,
                    lastName = lastName,
                    phone = phoneNumber,
                    id = contact!!.id
                )
                navigator().launchContactsListFragment()
            }
        }
    }

    companion object {

        private const val KEY_CONTACT = "CONTACT"

        @JvmStatic
        fun newInstance(contact: Contact?) =
            ContactDetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_CONTACT, contact)
                }
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}