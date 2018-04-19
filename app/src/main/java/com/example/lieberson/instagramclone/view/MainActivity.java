package com.example.lieberson.instagramclone.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.lieberson.instagramclone.R;
import com.example.lieberson.instagramclone.adapter.TabsAdapter;
import com.example.lieberson.instagramclone.util.SlidingTabLayout;
import com.example.lieberson.instagramclone.view.fragments.HomeFragment;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.IOException;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private android.support.v7.widget.Toolbar toolbarPrincipal;
    private SlidingTabLayout slidingTabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

       toolbarPrincipal.setLogo(R.drawable.instagramlogo);
       setSupportActionBar(toolbarPrincipal);

       //Configurando as abas


        //Configurando Adapter
        TabsAdapter tabsAdapter = new TabsAdapter(getSupportFragmentManager(), this);
        viewPager.setAdapter(tabsAdapter);
        slidingTabLayout.setCustomTabView(R.layout.tabs_view, R.id.text_item_tab);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setSelectedIndicatorColors(ContextCompat.getColor(this, R.color.cinzaEscuro));
        slidingTabLayout.setViewPager(viewPager);




    }

    private void initViews() {

        toolbarPrincipal = findViewById(R.id.toolbar_principal);
        slidingTabLayout = findViewById(R.id.sliding_tab_main);
        viewPager = findViewById(R.id.view_pager_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.action_sair :
                deslogarUsuario();
                return true;
            case R.id.action_configuracoes :

                return true;
            case R.id.action_compartilhar :
                compatilhar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //Com esse metodo vamos poder fazer o tratamento do retorno das imagens
        super.onActivityResult(requestCode, resultCode, data);

        //Testando o processo de retorno dos dados
        if (requestCode == 1 && resultCode == RESULT_OK && data != null){

            //Recuperando o local do recurso
            Uri localImagemSelecionada = data.getData();

            //Recuperando a imagem do local que foi selecionada
            try {

//                Bitmap imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada);
//
//                //Comprimir a imagem no formato png
//                ByteArrayOutputStream stream = new ByteArrayOutputStream();
//
//                imagem.compress(Bitmap.CompressFormat.PNG, 75, stream);
//
//                //Criando um array de bytes de imagens
//                byte[] byteArray = stream.toByteArray();

                ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(localImagemSelecionada, "r");

                FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);
                options.inSampleSize = calculateInSampleSize(options, 300, 300);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, options);

                parcelFileDescriptor.close();

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG,75,stream);
                byte[] byteArray = stream.toByteArray();

                SimpleDateFormat dateFormat = new SimpleDateFormat("ddmmaaaahhmmss");
                String nomeImagem = dateFormat.format(new Date());

                //Criando um arquivo com o formato do Parse
                ParseFile arquivoParse = new ParseFile(nomeImagem + "imagem.png", byteArray);

                //Montando objeto pasa salvar no parse
                ParseObject parseObject = new ParseObject("Imagem");
                parseObject.put("objectIdUser", ParseUser.getCurrentUser().getObjectId());
                parseObject.put("imagem", arquivoParse);

                //Salvando os dados
                parseObject.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        if (e == null){ //Sucesso

                            Toast.makeText(getApplicationContext(), "Sua imagem foi postada", Toast.LENGTH_SHORT).show();

                            //Atualizando a lista de itens do fragmento homeFragment
                            TabsAdapter adapterNovo = (TabsAdapter) viewPager.getAdapter();
                            HomeFragment homeFragmentNovo = (HomeFragment) adapterNovo.getFragment(0);
                            homeFragmentNovo.atualizaPostagens();

                        }else {

                            Toast.makeText(getApplicationContext(), "Erro ao postar a sua imagem - Tente novamente", Toast.LENGTH_SHORT).show();

                        }

                    }
                });

            } catch (IOException e) {

                e.printStackTrace();
            }

        }

    }

    private void compatilhar() {

        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI); //action = acao da intent || uri = identificador unico de um recurso
        startActivityForResult(intent, 1);
    }

    private void deslogarUsuario() {

        ParseUser.logOut();
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {

        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
