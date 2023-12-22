package com.yablunin.shopnstock.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yablunin.shopnstock.R
import com.yablunin.shopnstock.domain.util.Language
import com.yablunin.shopnstock.presentation.activities.HomeActivity
import com.yablunin.shopnstock.presentation.viewmodels.HomeViewModel

class LanguageAdapter(
    private val languages: MutableList<Language>,
    private val homeActivity: HomeActivity,
    private val context: Context
): RecyclerView.Adapter<LanguageAdapter.LanguageViewHolder>() {

    private val homeViewModel = homeActivity.viewModel
    class LanguageViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val holderLayout: LinearLayout = view.findViewById(R.id.language_item_layout)
        private val languageIcon: ImageView = view.findViewById(R.id.language_flag_icon)
        private val languageName: TextView = view.findViewById(R.id.language_name_text)
        fun bind(language: Language, viewModel: HomeViewModel, context: Context, homeActivity: HomeActivity){
            if (viewModel.isCurrentLanguage(language)){
                holderLayout.setBackgroundResource(R.drawable.language_item_current)
            } else{
                holderLayout.setOnClickListener {
                    viewModel.setAppLocale(context, language.languageCode)
                    homeActivity.recreate()
                }
            }
            languageIcon.setImageBitmap(viewModel.getFlagBitmap(language))
            languageName.text = language.language
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.language_item, parent, false)
        return LanguageViewHolder(view)
    }

    override fun getItemCount(): Int {
        return languages.size
    }

    override fun onBindViewHolder(holder: LanguageViewHolder, position: Int) {
        holder.bind(languages[position], homeViewModel, context, homeActivity)
    }
}