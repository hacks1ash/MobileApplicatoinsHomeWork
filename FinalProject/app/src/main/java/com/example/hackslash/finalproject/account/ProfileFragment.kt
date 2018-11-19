package com.example.hackslash.finalproject.account

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackslash.finalproject.BaseFragment
import com.example.hackslash.finalproject.R
import com.example.hackslash.finalproject.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_profile.*
import java.util.*

class ProfileFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        getProfilePicture()

        selectPhotoProfileFragmentButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 0)
        }

        updateData.setOnClickListener {
            uploadImageToFirebaseStorage()
        }
    }

    private var selectedPhotoUri: Uri? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPhotoUri = data.data

            val bitmap = MediaStore.Images.Media.getBitmap(activity?.contentResolver, selectedPhotoUri)

            profilePictureCircleView.setImageBitmap(bitmap)

            selectPhotoProfileFragmentButton.alpha = 0f
        }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return

        val filename = UUID.randomUUID().toString()
        val reference = FirebaseStorage.getInstance().getReference("/images/$filename")

        reference.putFile(selectedPhotoUri!!)
            .addOnSuccessListener {
                Log.d(TAG, "ProfilePictureUpdate : Success")

                reference.downloadUrl.addOnSuccessListener {
                    Log.d(TAG, "File Location : $it")

                    saveUserInfoToFirebaseDatabase(it.toString())
                }
            }
    }

    private fun saveUserInfoToFirebaseDatabase(profilePictureUrl: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val email = FirebaseAuth.getInstance().currentUser?.email
        val displayName = FirebaseAuth.getInstance().currentUser?.displayName
        val reference = FirebaseDatabase.getInstance().getReference("/users/$uid")

        val user = User(uid!!, email!!, displayName!!, profilePictureUrl)
        reference.setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Saved user to Database")
                    messagesFragment()
                }
            }

    }

    private fun getProfilePicture() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val reference = FirebaseDatabase.getInstance().getReference("/users")
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                p0.children.forEach {
                    val userInDatabase = it.getValue(User::class.java)
                    if (userInDatabase != null && currentUser!!.uid == userInDatabase.uid && userInDatabase.profileImageUrl != "") {
                        Picasso.get().load(userInDatabase.profileImageUrl).into(profilePictureCircleView)
                        selectPhotoProfileFragmentButton.visibility = View.GONE
                    }
                }

            }

            override fun onCancelled(p0: DatabaseError) {
            }
        })
    }


    companion object {
        private const val TAG = "ProfileFragment"
    }
}