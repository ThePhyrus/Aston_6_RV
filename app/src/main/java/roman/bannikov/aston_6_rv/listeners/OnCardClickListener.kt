package roman.bannikov.aston_6_rv.listeners

import roman.bannikov.aston_6_rv.model.Contact

interface OnCardClickListener {
    fun onCardClick(contact: Contact)
    fun onCardLongClick(id: Int?): Boolean
}