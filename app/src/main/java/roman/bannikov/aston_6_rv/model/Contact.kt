package roman.bannikov.aston_6_rv.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Contact(
    val id: Int?,
    val name: String?,
    val lastname: String?,
    val phoneNumber: String?
) : Parcelable
