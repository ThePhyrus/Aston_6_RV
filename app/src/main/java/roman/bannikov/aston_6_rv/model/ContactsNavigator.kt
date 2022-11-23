package roman.bannikov.aston_6_rv.model

import androidx.fragment.app.Fragment


interface ContactsNavigator {
    fun launchContactsListFragment()
    fun launchContactDetailsFragment(contact: Contact?)
}

fun Fragment.navigator(): ContactsNavigator {
    return requireActivity() as ContactsNavigator
}