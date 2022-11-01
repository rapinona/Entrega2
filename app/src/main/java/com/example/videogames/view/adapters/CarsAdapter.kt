import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.videogames.R
import com.example.videogames.databinding.ListElementBinding
import com.example.videogames.model.Car
import com.example.videogames.view.activities.MainActivity

class CarsAdapter(private val context: Context, val cars: ArrayList<Car>): RecyclerView.Adapter<CarsAdapter.ViewHolder>(){

    private val layoutInflater = LayoutInflater.from(context)

    class ViewHolder(view: ListElementBinding): RecyclerView.ViewHolder(view.root){
        val tvTitle = view.tvTitle
        val tvGenre = view.tvGenre
        val tvDeveloper = view.tvDeveloper
        val image = view.imageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListElementBinding.inflate(layoutInflater)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.tvTitle.text = cars[position].modelo
        holder.tvGenre.text = cars[position].ano.toString()
        holder.tvDeveloper.text = cars[position].color
        when(cars[position].marca){
            "BMW" -> {
                holder.image.setImageResource(R.drawable.bmw)
            }
            "Mercedes Benz" -> {
                holder.image.setImageResource(R.drawable.mercedes)
            }
            "Audi" -> {
                holder.image.setImageResource(R.drawable.audi)
            }
            else ->{
                holder.image.setImageResource(R.drawable.audi)
            }
        }


        //Para los clicks de cada elemento viewholder

        holder.itemView.setOnClickListener {
            //Manejar el click
            if(context is MainActivity) context.selectedCar(cars[position])
        }

    }

    override fun getItemCount(): Int {
        return cars.size
    }

}