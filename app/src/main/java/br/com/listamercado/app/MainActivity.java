package br.com.listamercado.app;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txt_produto;
    ListView list_view;
    ProdutoAdapter adapter;
    View.OnClickListener clickCk = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            CheckBox ck = (CheckBox) view;

            int position = (int) ck.getTag();

            Produto produtoSelecionado = adapter.getItem(position);

            Produto produtoDB = Produto.findById(Produto.class, produtoSelecionado.getId());

            if(ck.isChecked()){
                Toast.makeText(MainActivity.this, "Checado", Toast.LENGTH_SHORT).show();
                produtoSelecionado.setAtivo(true);
                produtoDB.setAtivo(true);
                produtoDB.save();
            }else{
                produtoSelecionado.setAtivo(false);
                produtoDB.setAtivo(false);
                produtoDB.save();
            }

            adapter.notifyDataSetChanged();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        list_view = (ListView) findViewById(R.id.list_produtos);
        txt_produto = (EditText) findViewById(R.id.txt_produto);

        //Executando SELECT da lista no banco usando ORM
        //LIST NÃO É UMA CLASSE, É UMA INTERFACE
        //Na criação é aconselhavel usar dessa forma: List<> lst = new ArrayList<>();
        List<Produto> lstProduto = Produto.listAll(Produto.class);

        adapter = new ProdutoAdapter(this, lstProduto);

        list_view.setAdapter(adapter);
    }

    public void inserirItem(View view) {


        String produto = txt_produto.getText().toString();

        if(produto.isEmpty()){
            txt_produto.setError("Insira um produto");
            return;
        }
        //Criando OBJ produto
        Produto p = new Produto(produto, false);

        //Adicionando na lista
        adapter.add(p);

        //Adicionando no Banco com ORM
        p.save();

        //Zerando caixa para inserir
        txt_produto.setText(null);

    }

    //Classe do Adapter
    private class ProdutoAdapter extends ArrayAdapter<Produto>{

        public ProdutoAdapter(Context context, List<Produto> items){
            super(context, 0, items);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            View v = convertView;

            if(v == null){

                v = LayoutInflater.from(getContext())
                        .inflate(R.layout.item_lista, null);

            }

            Produto item = getItem(position);

            TextView itemProduto = v.findViewById(R.id.txt_item_produto);
            CheckBox ativo = v.findViewById(R.id.ck_item_produto);

            itemProduto.setText(item.getNome());
            ativo.setChecked(item.isAtivo());

            //guardando posição do item
            ativo.setTag(position);

            ativo.setOnClickListener(clickCk);

            return v;
        }
    }

}
