package com.flip.warranty.retailer.rcvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flip.warranty.R
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.uitility.BuyNowClickInterface
import com.flip.warranty.databinding.ProductDetailSingleBinding
import com.squareup.picasso.Picasso

class MainRetailerRcvAdapter(
    private val clickInterface: BuyNowClickInterface,
    private val productList: ArrayList<ProductDetailsData>
) : RecyclerView.Adapter<MainRetailerRcvAdapter.RetailerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RetailerViewHolder {
        return RetailerViewHolder(
            ProductDetailSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun getItemCount() = productList.size


    override fun onBindViewHolder(holder: RetailerViewHolder, position: Int) {
        holder.binding.ProductName.text = productList[position].prodDisplayName
        holder.binding.ProductSummary.text = productList[position].manufacturer
        holder.binding.ProductPrice.text = productList[position].price
        val imageURL = productList[position].image
        if (imageURL.isNotBlank()) {
            Picasso
                .get()
                .load(imageURL)
                .placeholder(R.drawable.ic_baseline_add_photo_alternate_24)
                .into(holder.binding.productImage)
        }

        holder.binding.buyBtn.setOnClickListener {
            clickInterface.onClick(position)
        }
    }

    class RetailerViewHolder(val binding: ProductDetailSingleBinding) :
        RecyclerView.ViewHolder(binding.root)

}