package fr.enssat.gazouilli.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Hugo on 19/01/2016.
 */
public class CommentSQLBase extends SQLiteOpenHelper {
    private static final String TABLE_COMMENT = "table_comments";
    private static final String COL_ID = "ID";
    private static final String COL_ID_TWEET = "IDTWEET";
    private static final String COL_TEXT = "Text";
    private static final String COL_AUTHOR = "Author";

    private static final String CREATE_BDD = "CREATE TABLE " + TABLE_COMMENT + " ("
            + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL_ID_TWEET + " TEXT NOT NULL, "
            + COL_TEXT + " TEXT NOT NULL," + COL_AUTHOR + " TEXT NOT NULL);";

    public CommentSQLBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //on créé la table à partir de la requête écrite dans la variable CREATE_BDD
        db.execSQL(CREATE_BDD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //On peut fait ce qu'on veut ici moi j'ai décidé de supprimer la table et de la recréer
        //comme ça lorsque je change la version les id repartent de 0
        db.execSQL("DROP TABLE " + TABLE_COMMENT + ";");
        onCreate(db);
    }
}
