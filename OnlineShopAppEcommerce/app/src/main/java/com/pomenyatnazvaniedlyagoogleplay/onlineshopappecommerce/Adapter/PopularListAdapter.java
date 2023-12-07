package com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Activity.DetailActivity;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.Domain.PopularDomain;
import com.pomenyatnazvaniedlyagoogleplay.onlineshopappecommerce.R;

import java.util.ArrayList;
//для вывода сложных объектов в RecyclerView необходимо определить свой адаптер.
public class PopularListAdapter extends RecyclerView.Adapter<PopularListAdapter.ViewHolder> {
    //PopularListAdapter - название адаптера,
    //класс RecyclerView.Adapter от которого наследуется PopularListAdapter, определяет 3 метода
    //Для хранения данных в классе адаптера определен статический класс ViewHolder,
    // который использует определенные в list_item.xml элементы управления.
    ArrayList<PopularDomain> items; //создаем массив с типом данных PopularDomain, и называем его items
    Context context;


    public PopularListAdapter(ArrayList<PopularDomain> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public PopularListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //onCreateViewHolder - возвращает объект ViewHolder, который будет хранить данные по одному объекту PopularDomain.
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_pop_list, parent, false);
        //создаем объект inflate, который состоит из элементов макета viewholder_pop_list
        context = parent.getContext();
        return new ViewHolder(inflate); //возвращается объект ViewHolder, который хранит данные по одному объекту PopularDomain.
    }

    @Override
    public void onBindViewHolder(@NonNull PopularListAdapter.ViewHolder holder, int position) {
        //выполняет привязку объекта ViewHolder к объекту PopularDomain по определенной позиции
        // holder - держатель
        holder.titleTxt.setText(items.get(position).getTitle()); //считывает заголовок с объекта по позиции
        holder.feeTxt.setText("" + items.get(position).getPrice()); //считывает цену с объекта по позиции
        holder.ScoreTxt.setText("" + items.get(position).getScore()); //считывает количество с объекта по позиции

        int drawableResourceId = holder.itemView.getResources().getIdentifier(items.get(position)
                .getPicUrl(), "drawable", holder.itemView.getContext().getPackageName());

        Glide.with(holder.itemView.getContext()) //-with(Context context) — with(c)передаём Context
                .load(drawableResourceId) //-load(String imageUrl) — load(загружать) указываем адрес картинки из интернета, ресурса, файла
                .transform(new GranularRoundedCorners(30, 30, 0, 0))
                .into(holder.pic);//-Into(ImageView targetImageView) — компонент ImageView , в котором отобразиться картинка
         //Библиотека Glide является ближайшим конкурентом другой популярной библиотеке
        // Picasso и также предназначена для асинхронной подгрузки изображений котов из сети,
        // ресурсов или файловой системы, их кэширования и отображения.


        //функция для перехода на другую активность с карточкой товара
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(holder.itemView.getContext(), DetailActivity.class);//перемещение на карточку товара при клике,
            //это конструкция кода для перехода на другую активность
            // intent - это намерение, запуск другой активности
            intent.putExtra("object", items.get(position));//Также при переходе на другую активность мы можем указать какие-то
            // данные, а принимающая активность должна уметь обработать их. Для этих целей существуют методы типа putXXX().
            holder.itemView.getContext().startActivity(intent);
            // Активность является подклассом Context,
        });
    }

    @Override
    public int getItemCount() { //возвращает количество объектов в списке
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //Для хранения данных в классе адаптера определен статический класс ViewHolder,
        // который использует определенные в list_item.xml элементы управления.
        //Следует учитывать, что RecyclerView расположен в пакете androidx.recyclerview.widget
        // и является частью тулкита Android Jetpack, поэтому при использовании виджета указывается
        // полное его название с учетом пакета (в принципе как и для ConstraintLayout):
        TextView titleTxt, feeTxt, ScoreTxt;
        ImageView pic;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleTxt = itemView.findViewById(R.id.titleTxt);
            feeTxt = itemView.findViewById(R.id.feeTxt);
            ScoreTxt = itemView.findViewById(R.id.scoreText);
            pic = itemView.findViewById(R.id.pic);
        }
    }
}
