package com.example.myapplication.Fragment_User.Profile

import DatabaseHelper
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.myapplication.Activity.MainActivity
import com.example.myapplication.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Fragment_profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Fragment_profile : Fragment() {
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var btn_logout: Button;
    private lateinit var btn_update: Button;
    private lateinit var txt_tt: TextView;
    private lateinit var txt_idkh: TextView;
    private var accountId: String = "null"
    private var khid: Long = -1
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.user_profile, container, false)
        dbHelper = DatabaseHelper(requireContext())
        arguments?.let {
            accountId = it.getString("account_id", accountId  )
        }

        btn_logout = view.findViewById(R.id.btn_logout)
        btn_update = view.findViewById(R.id.btn_update)


        btn_logout.setOnClickListener {
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
        }

        btn_update.setOnClickListener {
            val intent = Intent(requireContext(), Profile_update::class.java)
            intent.putExtra("account_id", accountId)
            startActivity(intent)
        }

        return view
    }

}