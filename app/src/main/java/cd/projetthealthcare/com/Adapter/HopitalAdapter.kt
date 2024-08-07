package cd.projetthealthcare.com.Adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import cd.projetthealthcare.com.Model.Hopital
import cd.projetthealthcare.com.R
import com.google.android.material.card.MaterialCardView
import java.util.Random

class HopitalAdapter(val item:ArrayList<Hopital>):
    RecyclerView.Adapter<HopitalAdapter.myviewholder>() {

    private var onItemClickListener: OnItemClickListener? = null
    fun setOnItemClickListener(listener: OnItemClickListener){
        onItemClickListener = listener
    }
    interface OnItemClickListener{
        fun onItemClick(item: Hopital)
    }
    class myviewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(hopital: Hopital) {

           // Glide.with(itemView).load(hopital.image).into(itemView.findViewById<ImageView>(R.id.image_hopital))
            //prendre la premiere lettre du nom de l'hopital
            val firstLetter = hopital.nom[0]
            itemView.findViewById<TextView>(R.id.tv_hopital_name).text = hopital.nom
            //changer la backgroundtint
            val name = itemView.findViewById<TextView>(R.id.tv_short_name)
            name.text = firstLetter.toString()

            itemView.findViewById<MaterialCardView>(R.id.contenaire_item).backgroundTintList =
                if (hopital.isSelected) {
                    val color = ContextCompat.getColorStateList(itemView.context, R.color.Accent)
                    color
                } else {
                    //on laisse la couleur par defaut
                    null
                }




        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.choice_hop_item,parent,false)
        return myviewholder(view)
    }

    override fun onBindViewHolder(holder: myviewholder, position: Int) {
        holder.bind(item[position])
        val r = Random()
        val red: Int = r.nextInt(255 - 0 + 1) + 0
        val green: Int = r.nextInt(255 - 0 + 1) + 0
        val blue: Int = r.nextInt(255 - 0 + 1) + 0
        val shape = GradientDrawable()
        shape.shape = GradientDrawable.OVAL
        shape.setColor(Color.rgb(red, green, blue))
        holder.itemView.findViewById<TextView>(R.id.tv_short_name).background = shape

        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(item[position])
            for (i in item){
                i.isSelected = false
            }
            item[position].isSelected = true
            notifyDataSetChanged()
        }


    }

    override fun getItemCount(): Int {
        return item.size
    }
}