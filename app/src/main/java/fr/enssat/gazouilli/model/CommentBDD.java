package fr.enssat.gazouilli.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hugo on 19/01/2016.
 */
public class CommentBDD {
    private static final int VERSION_BDD = 1;
    private static final String NOM_BDD = "gazouilli.db";

    private static final String TABLE_COMMENT = "table_comments";
    private static final String COL_ID = "ID";
    private static final int NUM_COL_ID = 0;
    private static final String COL_ID_TWEET = "IDTWEET";
    private static final int NUM_COL_ID_TWEET = 1;
    private static final String COL_TEXT = "Text";
    private static final int NUM_COL_TEXT = 2;
    private static final String COL_AUTHOR = "Author";
    private static final int NUM_COL_AUTHOR = 3;

    private SQLiteDatabase bdd;

    private CommentSQLBase mySQLBase;

    public CommentBDD(Context context){
        //On créer la BDD et sa table
        mySQLBase = new CommentSQLBase(context, NOM_BDD, null, VERSION_BDD);
    }

    public void open(){
        //on ouvre la BDD en écriture
        bdd = mySQLBase.getWritableDatabase();
    }

    public void close(){
        //on ferme l'accès à la BDD
        bdd.close();
    }

    public SQLiteDatabase getBDD(){
        return bdd;
    }

    public long insertComment(Comment comment){
        //Création d'un ContentValues (fonctionne comme une HashMap)
        ContentValues values = new ContentValues();
        //on lui ajoute une valeur associé à une clé (qui est le nom de la colonne dans laquelle on veut mettre la valeur)
        values.put(COL_ID_TWEET, comment.getTweetId());
        values.put(COL_TEXT, comment.getText());
        values.put(COL_AUTHOR, comment.getAuthor());
        //on insère l'objet dans la BDD via le ContentValues
        return bdd.insert(TABLE_COMMENT, null, values);
    }

    public int updateComment(int id, Comment comment){
        //La mise à jour d'un comment dans la BDD fonctionne plus ou moins comme une insertion
        //il faut simple préciser quelle comment on doit mettre à jour grâce à l'ID
        ContentValues values = new ContentValues();
        values.put(COL_ID_TWEET, comment.getTweetId());
        values.put(COL_TEXT, comment.getText());
        values.put(COL_AUTHOR, comment.getAuthor());
        return bdd.update(TABLE_COMMENT, values, COL_ID + " = " +id, null);
    }
    public int removeCommentWithID(int id){
        //Suppression d'un comment de la BDD grâce à l'ID
        return bdd.delete(TABLE_COMMENT, COL_ID + " = " + id, null);
    }

    public List<Comment> getCommentsWithTweetID(String tweetId){
        //Récupère dans un Cursor les valeur correspondant aux comments contenus dans la BDD (ici on sélectionne les comments grâce au tweet id)
        Cursor c = bdd.query(TABLE_COMMENT, new String[] {COL_ID, COL_ID_TWEET, COL_TEXT, COL_AUTHOR}, COL_ID_TWEET + " LIKE \"" + tweetId +"\"", null, null, null, null);
        return cursorToComment(c);
    }
    
    private List<Comment> cursorToComment(Cursor c){
        //si aucun élément n'a été retourné dans la requête, on renvoie null
        List<Comment> comments = new ArrayList<Comment>();
        if (c.getCount() == 0)
            return null;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            Comment comment = new Comment(c.getString(NUM_COL_ID_TWEET),c.getString(NUM_COL_AUTHOR),c.getString(NUM_COL_TEXT));
            comments.add(comment);
        }
        c.close();

        //On retourne la liste de comment
        return comments;
    }

}
