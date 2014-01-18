package cz.appvision.ebookreader;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class BookShelfFragment extends Fragment implements OnClickListener {
	
	private int[] knihyNaPredaj = { R.drawable.ac_kapestni,
			R.drawable.ac_svyslovnosti, R.drawable.angl_kolibri,
			R.drawable.angl_podstat_jm, R.drawable.anglictina_pro_au, R.drawable.biologie,
			R.drawable.blbologie, R.drawable.cestujeme, R.drawable.clovek_jako_obcan,
			R.drawable.clovek_jako_osobnost, R.drawable.clovek_na_ceste, R.drawable.clovek_ve_spolecnosti,
			R.drawable.francouzstina_pro_au, R.drawable.hlasky_slovo_veta, R.drawable.ital_kolibri,
			R.drawable.prehled_fyziky, R.drawable.prehled_matem, R.drawable.prehled_nem_gramatiky,
			R.drawable.prehled_org_chemie, R.drawable.prehled_ruske_gram, R.drawable.prehled_tvaroslovi,
			R.drawable.spanelstina_pro_au, R.drawable.ukrajinsky_kapes};

	private List<Ebook> ebooksForSale;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.bookshelf, null);
		
		LinearLayout polica1 = (LinearLayout) view.findViewById(R.id.polica1);
		LinearLayout polica2 = (LinearLayout) view.findViewById(R.id.polica2);
		LinearLayout polica3 = (LinearLayout) view.findViewById(R.id.polica3);
		LinearLayout polica4 = (LinearLayout) view.findViewById(R.id.polica4);
		LinearLayout polica5 = (LinearLayout) view.findViewById(R.id.polica5);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
		params.weight = 1.0F;
		
		ImageView iv1 = new ImageView(getActivity());
		iv1.setImageResource(knihyNaPredaj[0]);
		iv1.setLayoutParams(params);
		polica1.addView(iv1);
		
		ebooksForSale = new ArrayList<Ebook>();
		
		for (int i = 0; i<  knihyNaPredaj.length; i++){
			ebooksForSale.add(new Ebook(knihyNaPredaj[i], false));
			ImageView iv = new ImageView(getActivity());
			iv.setImageResource(knihyNaPredaj[i]);
			iv.setLayoutParams(params);
			iv.setOnClickListener(this);
			if (i < 6)	{polica2.addView(iv); continue;}
			if (i < 12)	{polica3.addView(iv); continue;}
			if (i < 18)	{polica4.addView(iv); continue;}
			if (i < knihyNaPredaj.length)	{polica5.addView(iv); continue;}
		}
		
	
		return view;
	}

	@Override
	public void onClick(View v) {
		FragmentTransaction transaction = getActivity().getSupportFragmentManager()
				.beginTransaction();
		
		EbookDetailFragment ebookDetailFragment = new EbookDetailFragment();

		transaction.replace(R.id.contentFrameLayout, ebookDetailFragment);

		transaction.addToBackStack(null);
		transaction.commit();
	}

}
