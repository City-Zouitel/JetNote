package com.example.local.daos;

import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.local.model.Note;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NotesDao_Impl implements NotesDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Note> __insertionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter<Note> __deletionAdapterOfNote;

  private final EntityDeletionOrUpdateAdapter<Note> __updateAdapterOfNote;

  private final SharedSQLiteStatement __preparedStmtOfDeleteAllTrashedNotes;

  public NotesDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNote = new EntityInsertionAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR IGNORE INTO `Notes` (`Uid`,`Title`,`Description`,`Priority`,`Color`,`TextColor`,`Date`,`Trashed`,`AudioDuration`,`Reminding`,`ImageUrl`,`AudioUrl`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getPriority() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPriority());
        }
        stmt.bindLong(5, value.getColor());
        stmt.bindLong(6, value.getTextColor());
        if (value.getDate() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDate());
        }
        stmt.bindLong(8, value.getTrashed());
        stmt.bindLong(9, value.getAudioDuration());
        stmt.bindLong(10, value.getReminding());
        if (value.getImageUrl() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getImageUrl());
        }
        if (value.getAudioUrl() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAudioUrl());
        }
      }
    };
    this.__deletionAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `Notes` WHERE `Uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
      }
    };
    this.__updateAdapterOfNote = new EntityDeletionOrUpdateAdapter<Note>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR REPLACE `Notes` SET `Uid` = ?,`Title` = ?,`Description` = ?,`Priority` = ?,`Color` = ?,`TextColor` = ?,`Date` = ?,`Trashed` = ?,`AudioDuration` = ?,`Reminding` = ?,`ImageUrl` = ?,`AudioUrl` = ? WHERE `Uid` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Note value) {
        if (value.getUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getUid());
        }
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getPriority() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getPriority());
        }
        stmt.bindLong(5, value.getColor());
        stmt.bindLong(6, value.getTextColor());
        if (value.getDate() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getDate());
        }
        stmt.bindLong(8, value.getTrashed());
        stmt.bindLong(9, value.getAudioDuration());
        stmt.bindLong(10, value.getReminding());
        if (value.getImageUrl() == null) {
          stmt.bindNull(11);
        } else {
          stmt.bindString(11, value.getImageUrl());
        }
        if (value.getAudioUrl() == null) {
          stmt.bindNull(12);
        } else {
          stmt.bindString(12, value.getAudioUrl());
        }
        if (value.getUid() == null) {
          stmt.bindNull(13);
        } else {
          stmt.bindString(13, value.getUid());
        }
      }
    };
    this.__preparedStmtOfDeleteAllTrashedNotes = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "delete from NOTES where Trashed = 1";
        return _query;
      }
    };
  }

  @Override
  public Object addNote(final Note note, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNote.insert(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteNote(final Note note, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNote.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object editNote(final Note note, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfNote.handle(note);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteAllTrashedNotes(final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteAllTrashedNotes.acquire();
        __db.beginTransaction();
        try {
          _stmt.executeUpdateDelete();
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
          __preparedStmtOfDeleteAllTrashedNotes.release(_stmt);
        }
      }
    }, continuation);
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
