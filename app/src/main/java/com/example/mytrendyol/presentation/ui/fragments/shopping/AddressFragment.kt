package com.example.mytrendyol.presentation.ui.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.mytrendyol.R
import com.example.mytrendyol.databinding.FragmentAddressBinding
import com.example.mytrendyol.presentation.ui.adapters.shopping.UserInfoAdapter
import com.example.mytrendyol.presentation.ui.viewmodels.shopping.ShoppingCardViewModel
import com.example.mytrendyol.presentation.ui.models.shopping.UserShopModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private lateinit var binding: FragmentAddressBinding
    private  val viewModel: ShoppingCardViewModel by viewModels()
    private lateinit var adapter: UserInfoAdapter
    private  var list= listOf<UserShopModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddressBinding.inflate(layoutInflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter= UserInfoAdapter()
        viewModel.getUserInfo(requireContext())
        setAdapter()
        backView()
    }
    private fun setAdapter(){
        viewModel.info.observe(viewLifecycleOwner){ list->
            list?.let {
                this.list=it
                adapter.submitList(list)
                binding.rcycleview.adapter=adapter
            }
        }
    }

    private fun backView(){
        binding.imageView31.setOnClickListener {
            findNavController().navigate(R.id.action_addressFragment_to_accountSettingsFragment)
        }
    }

}