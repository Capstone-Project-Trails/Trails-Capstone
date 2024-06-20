package com.bangkitcapstone.trails.ui.aichat

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkitcapstone.trails.R
import com.bangkitcapstone.trails.adapter.Message
import com.bangkitcapstone.trails.adapter.ChatAdapter
import com.bangkitcapstone.trails.adapter.PopularAdapter
import com.bangkitcapstone.trails.databinding.ActivityAichatBinding
import com.bangkitcapstone.trails.factory.ViewModelFactory
import com.bangkitcapstone.trails.ui.home.HomeViewModel
import com.bangkitcapstone.trails.utils.Result
import com.google.android.material.internal.ViewUtils.hideKeyboard

class AIChatActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAichatBinding
    private var messages = mutableListOf<Message>()
    private val factory: ViewModelFactory = ViewModelFactory.getInstance()
    private val aiViewModel: AIChatViewModel by viewModels {
        factory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAichatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener {
            onBackPressed()
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvChat.layoutManager = layoutManager
        val adapter = ChatAdapter(messages)
        binding.rvChat.adapter = adapter

        binding.btnSend.setOnClickListener{
            val message = binding.tietInputTextEditText.text.toString()
            if (message.isNotEmpty()) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.tietInputTextEditText.windowToken, 0)

                messages.add(Message(message, true))

                adapter.notifyItemInserted(messages.size - 1)
                binding.rvChat.scrollToPosition(messages.size - 1)
                binding.tietInputTextEditText.text?.clear()

                sendMessageToAI(message, adapter)
            }
        }

        sendInitialMessage()
    }

    private fun sendInitialMessage() {
        val initialMessage = getString(R.string.initial_aichat)
        messages.add(Message(initialMessage, false))
    }

    private fun sendMessageToAI(message: String, adapter: ChatAdapter) {
        aiViewModel.getResponse(message).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        messages.add(Message(getString(R.string.loading_aichat), false))
                        adapter.notifyItemInserted(messages.size - 1)
                    }

                    is Result.Success -> {
                        var counter = 1
                        var response = ""

                        if (result.data.suggestions.isEmpty()) {
                            response += getString(R.string.missing_aichat)
                        } else {
                            response += result.data.response
                            for (place in result.data.suggestions) {
                                response += "\n${counter}. ${place.name}"
                                counter++
                            }
                        }

                        removeLoading(adapter)

                        messages.add(Message(response, false))
                        adapter.notifyItemInserted(messages.size - 1)
                    }

                    is Result.Error -> {
                        removeLoading(adapter)

                        showToast(result.error)
                    }
                }
            }
        }
    }

    private fun removeLoading(adapter: ChatAdapter) {
        val index = messages.indexOfFirst { it.text == "Loading..." && !it.isUser }
        if (index != -1) {
            messages.removeAt(index)
        }

        adapter.notifyItemRemoved(index)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
