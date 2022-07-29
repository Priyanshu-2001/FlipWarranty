package com.flip.warranty.customer.rcvAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.flip.warranty.R
import com.flip.warranty.customer.dataModel.ProductDetailsData
import com.flip.warranty.customer.uitility.BuyNowClickInterface
import com.flip.warranty.databinding.ProductDetailSingleBinding
import com.squareup.picasso.Picasso

class BuyNowAdapter(
    private val productList: ArrayList<ProductDetailsData>,
    val clickInterface: BuyNowClickInterface
) :
    RecyclerView.Adapter<BuyNowAdapter.BuyViewHolder>() {

    class BuyViewHolder(val binding: ProductDetailSingleBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuyViewHolder {
        return BuyViewHolder(
            ProductDetailSingleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: BuyViewHolder, position: Int) {
        if (productList[position].soldStatus == "0") {
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
        }
        holder.binding.buyBtn.setOnClickListener {
            clickInterface.onClick(position)
        }
    }

    override fun getItemCount() = productList.size
}