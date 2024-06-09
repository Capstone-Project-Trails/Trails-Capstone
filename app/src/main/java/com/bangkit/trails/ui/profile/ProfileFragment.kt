package com.bangkit.trails.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.lifecycleScope
import com.bangkit.trails.R
import com.bangkit.trails.databinding.FragmentProfileBinding
import com.bangkit.trails.ui.welcome.WelcomeActivity
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.launch

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding as FragmentProfileBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = Firebase.auth
        val user = auth.currentUser
        val photoUrl = user?.photoUrl.toString()
        val highResPhotoUrl = photoUrl.replace("s96-c", "s200-c")

        binding.profileName.text = "${user?.displayName}"
        Glide.with(this).load(highResPhotoUrl).into(binding.profileImage)

        binding.logout.setOnClickListener {
            AlertDialog.Builder(requireActivity()).apply {
                setTitle("Logout")
                setMessage(getString(R.string.info_logout))
                setPositiveButton(getString(R.string.yes)) { _, _ ->
                    signOut()
                }
                setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
                create()
                show()

            }
        }
    }

    private fun signOut() {
        lifecycleScope.launch {
            val credentialManager = CredentialManager.create(requireActivity())

            auth.signOut()
            credentialManager.clearCredentialState(ClearCredentialStateRequest())

            val intent = Intent(requireActivity(), WelcomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}
