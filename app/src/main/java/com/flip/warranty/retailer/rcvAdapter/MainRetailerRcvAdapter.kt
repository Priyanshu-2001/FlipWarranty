package com.flip.warranty.retailer.rcvAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flip.warranty.R
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.databinding.SingleProductDetailBinding
import com.flip.warranty.retailer.utility.onClickItemRetailer
import com.squareup.picasso.Picasso

class MainRetailerRcvAdapter(
    private val clickInterface: onClickItemRetailer,
    private val productList: ArrayList<ProductDetailsData>,
    private val type: Int
) : RecyclerView.Adapter<MainRetailerRcvAdapter.RetailerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetailerViewHolder {
        return RetailerViewHolder(
            SingleProductDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent, false
            )
        )
    }

    override fun getItemCount() = productList.size

    override fun onBindViewHolder(holder: RetailerViewHolder, position: Int) {
        holder.binding.ProductName.text = productList[position].prodDisplayName
        holder.binding.ProductSummary.text = productList[position].manufacturer
        holder.binding.ProductPrice.text =
            StringBuilder().append("\u20B9").append(" " + productList[position].price)
        if (type == 1)
            holder.binding.specialBtn.visibility = View.INVISIBLE
        else if (type == 2) {
            holder.binding.specialBtn.text = "Sign The Item"
        } else
            holder.binding.specialBtn.visibility = View.INVISIBLE


        val imageURL = productList[position].image

        if (imageURL.isNotBlank()) {
            Picasso
                .get()
                .load(imageURL)
                .placeholder(R.drawable.ic_baseline_add_photo_alternate_24)
                .into(holder.binding.productImage)
        }

        holder.binding.specialBtn.setOnClickListener {
            clickInterface.onclick(position, productList[position], type)
        }
    }

    class RetailerViewHolder(val binding: SingleProductDetailBinding) :
        RecyclerView.ViewHolder(binding.root)

}