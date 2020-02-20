package tricksfair.someshagra01.tricksfair;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.bumptech.glide.Glide;
import java.util.List;

public class slideshowAdapter extends PagerAdapter {

    private Context context;
    LayoutInflater inflater;
    public List<String> arrayList;
    public List<String> slideImageUrl;
    CustomTabs customTabs;

    public slideshowAdapter(Context context, List<String> arrayList,List<String> slideImageUrl){

        this.context = context;
        this.arrayList = arrayList;
        this.slideImageUrl = slideImageUrl;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.imagescrollview,container,false);
        ImageView img = (ImageView) view.findViewById(R.id.imageView);
        //img.setImageResource(images[position]);
        Glide.with(context).load(arrayList.get(position).trim()).into(img);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customTabs.openTab(context,slideImageUrl.get(position));
            }
        });

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }

    @Override
    public int getCount() {
        if(arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view==(LinearLayout)object);
    }
}
