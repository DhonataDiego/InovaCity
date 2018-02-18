package br.ufrpe.josed.inovacity.repositorio;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.ufrpe.josed.inovacity.database.DBConnection;
import br.ufrpe.josed.inovacity.model.*;

/**
 * Created by josed on 16/02/2018.
 */



public class PublicacaoDAO {
    private SQLiteDatabase conexao;

    public PublicacaoDAO(Context context){
        conexao = DBConnection.criarConexao(context);

    }

    public long inserir(br.ufrpe.josed.inovacity.model.Publicacao publicacao){

        ContentValues values = new ContentValues();
        values.put("titulo",publicacao.getTitulo());
        values.put("descricao",publicacao.getDescricao());
      //  values.put("imagens",publicacao.getImagens());
        values.put("latitude",publicacao.getLatitude());
        values.put("longitude",publicacao.getLongitude());
        values.put("apoios",publicacao.getApoios());
        values.put("nao_apoios",publicacao.getNaoApoios());

        values.put("data_abertura",publicacao.getDataAbertura().getTime());
        values.put("data_fechamento",(publicacao.getDataFechamento()!=null?publicacao.getDataFechamento().getTime():null));
        values.put("resolvida",publicacao.isResolvida());
        values.put("visivel",publicacao.isVisivel());
        values.put("url",publicacao.getUrl());

        return conexao.insertOrThrow("publicacao", null , values);
    }

    public boolean atualizar(br.ufrpe.josed.inovacity.model.Publicacao publicacao){
        ContentValues values = new ContentValues();
        values.put("titulo",publicacao.getTitulo());
        values.put("descricao",publicacao.getDescricao());

        //  values.put("imagens",publicacao.getImagens());
        values.put("latitude",publicacao.getLatitude());
        values.put("longitude",publicacao.getLongitude());
        values.put("apoios",publicacao.getApoios());
        values.put("nao_apoios",publicacao.getNaoApoios());

        values.put("data_abertura",publicacao.getDataAbertura().getTime());
        values.put("data_fechamento",publicacao.getDataFechamento().getTime());
        values.put("resolvida",publicacao.isResolvida());
        values.put("visivel",publicacao.isVisivel());
        values.put("url",publicacao.getUrl());


        String[] identificador = new String[1];
        identificador[0] = String.valueOf(publicacao.getId());


        int result =  conexao.update("publicacao", values, "id = ?",identificador );

        if(result==0) {
            return false;
        } else {
            return true;
        }    }

    public boolean remover(int id){

        String[] identificador = new String[1];
        identificador[0] = String.valueOf(id);

        int result = conexao.delete("publicacao", "id = ?",identificador );

        if(result==0) {
            return false;
        } else {
            return true;
        }
    }

    public br.ufrpe.josed.inovacity.model.Publicacao buscarPorID(int id){


        String chave[] = new String[1];
        chave[0] = String.valueOf(id);


        Cursor result = conexao.query("publicacao",null,"id = ?",chave,null,null,null);
        if(result.moveToFirst()) {
            br.ufrpe.josed.inovacity.model.Publicacao publicacao = new br.ufrpe.josed.inovacity.model.Publicacao();

            publicacao.setId(result.getLong(result.getColumnIndexOrThrow("id")));
            publicacao.setTitulo(result.getString(result.getColumnIndexOrThrow("titulo")));
          //  publicacao.setImagens(result.getString(result.getColumnIndexOrThrow("imagens")));
            publicacao.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
            publicacao.setLatitude(result.getLong(result.getColumnIndexOrThrow("latitude")));
            publicacao.setLongitude(result.getLong(result.getColumnIndexOrThrow("longitude")));
            publicacao.setApoios(result.getInt(result.getColumnIndexOrThrow("apoios")));
            publicacao.setNaoApoios(result.getInt(result.getColumnIndexOrThrow("nao_apoios")));

           // publicacao.setDataAbertura(result.getLong(result.getColumnIndexOrThrow("data_abertura")));
          //  publicacao.setDataFechamento(result.getLong(result.getColumnIndexOrThrow("data_fechamento")));
            publicacao.setResolvida(result.getInt(result.getColumnIndexOrThrow("resolvida"))>0);
            publicacao.setVisivel(result.getInt(result.getColumnIndexOrThrow("visivel"))>0);
            publicacao.setUrl(result.getString(result.getColumnIndexOrThrow("url")));

            return publicacao;
        }

        return null;    }

    public List<br.ufrpe.josed.inovacity.model.Publicacao> listarTodos(){
        List<Publicacao> publicacaoList = new ArrayList<Publicacao>();

        Cursor result = conexao.query("publicacao",null,null,null,null,null,null);
        if(result.getCount()>0){
            result.moveToFirst();
            do {
                Publicacao publicacao = new Publicacao();

                publicacao.setId(result.getLong(result.getColumnIndexOrThrow("id")));
                publicacao.setTitulo(result.getString(result.getColumnIndexOrThrow("titulo")));
                //  publicacao.setImagens(result.getString(result.getColumnIndexOrThrow("imagens")));
                publicacao.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
                publicacao.setLatitude(result.getLong(result.getColumnIndexOrThrow("latitude")));
                publicacao.setLongitude(result.getLong(result.getColumnIndexOrThrow("longitude")));
                publicacao.setApoios(result.getInt(result.getColumnIndexOrThrow("apoios")));
                publicacao.setNaoApoios(result.getInt(result.getColumnIndexOrThrow("nao_apoios")));

                // publicacao.setDataAbertura(result.getLong(result.getColumnIndexOrThrow("data_abertura")));
                //  publicacao.setDataFechamento(result.getLong(result.getColumnIndexOrThrow("data_fechamento")));
                publicacao.setResolvida(result.getInt(result.getColumnIndexOrThrow("resolvida"))>0);
                publicacao.setVisivel(result.getInt(result.getColumnIndexOrThrow("visivel"))>0);
                publicacao.setUrl(result.getString(result.getColumnIndexOrThrow("url")));

                publicacaoList.add(publicacao);
            }while (result.moveToNext());

            return publicacaoList;

        }

        return null;

    }
    public List<br.ufrpe.josed.inovacity.model.Publicacao> listarRecentes(){
        List<Publicacao> publicacaoList = new ArrayList<Publicacao>();

        Cursor result = conexao.query("publicacao",null,null,null,null,null,null, String.valueOf(10));
        if(result.getCount()>0){
            result.moveToFirst();
            do {
                Publicacao publicacao = new Publicacao();

                publicacao.setId(result.getLong(result.getColumnIndexOrThrow("id")));
                publicacao.setTitulo(result.getString(result.getColumnIndexOrThrow("titulo")));
                //  publicacao.setImagens(result.getString(result.getColumnIndexOrThrow("imagens")));
                publicacao.setDescricao(result.getString(result.getColumnIndexOrThrow("descricao")));
                publicacao.setLatitude(result.getLong(result.getColumnIndexOrThrow("latitude")));
                publicacao.setLongitude(result.getLong(result.getColumnIndexOrThrow("longitude")));
                publicacao.setApoios(result.getInt(result.getColumnIndexOrThrow("apoios")));
                publicacao.setNaoApoios(result.getInt(result.getColumnIndexOrThrow("nao_apoios")));

                // publicacao.setDataAbertura(result.getLong(result.getColumnIndexOrThrow("data_abertura")));
                //  publicacao.setDataFechamento(result.getLong(result.getColumnIndexOrThrow("data_fechamento")));
                publicacao.setResolvida(result.getInt(result.getColumnIndexOrThrow("resolvida"))>0);
                publicacao.setVisivel(result.getInt(result.getColumnIndexOrThrow("visivel"))>0);
                publicacao.setUrl(result.getString(result.getColumnIndexOrThrow("url")));

                publicacaoList.add(publicacao);
            }while (result.moveToNext());

            return publicacaoList;

        }

        return null;}
}