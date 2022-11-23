package roman.bannikov.aston_6_rv.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import roman.bannikov.aston_6_rv.R
import roman.bannikov.aston_6_rv.databinding.ActivityMainBinding
import roman.bannikov.aston_6_rv.model.Contact
import roman.bannikov.aston_6_rv.model.ContactsNavigator
import roman.bannikov.aston_6_rv.view.fragments.ContactDetailsFragment
import roman.bannikov.aston_6_rv.view.fragments.ContactsListFragment

class MainActivity : AppCompatActivity(), ContactsNavigator {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        if (savedInstanceState == null) {
            launchContactListFragment()
        }
    }

    override fun launchContactDetailsFragment(contact: Contact?) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.containerForFragment,
                ContactDetailsFragment.newInstance(contact = contact)
            )
            .addToBackStack(TAG_CONTACT_DETAILS_FRAGMENT)
            .commit()
    }

    override fun launchContactsListFragment() { //fixme херня какая-то, смотри ДЗ 5
        launchContactListFragment()
    }

    private fun launchContactListFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.containerForFragment,
                ContactsListFragment()
            )
            .addToBackStack(TAG_CONTACTS_LIST_FRAGMENT)
            .commit()
    }

    companion object {
        private const val TAG_CONTACTS_LIST_FRAGMENT = "ContactsListFragment"
        private const val TAG_CONTACT_DETAILS_FRAGMENT = "ContactDetailsFragment"
    }

}