package com.example.local.daos;

import android.database.Cursor;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.local.model.NoteAndLabel;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class NoteAndLabelDao_Impl implements NoteAndLabelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NoteAndLabel> __insertionAdapterOfNoteAndLabel;

  private final EntityDeletionOrUpdateAdapter<NoteAndLabel> __deletionAdapterOfNoteAndLabel;

  public NoteAndLabelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNoteAndLabel = new EntityInsertionAdapter<NoteAndLabel>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `note_and_label` (`noteUid`,`labelId`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteAndLabel value) {
        if (value.getNoteUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getNoteUid());
        }
        stmt.bindLong(2, value.getLabelId());
      }
    };
    this.__deletionAdapterOfNoteAndLabel = new EntityDeletionOrUpdateAdapter<NoteAndLabel>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `note_and_label` WHERE `noteUid` = ? AND `labelId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteAndLabel value) {
        if (value.getNoteUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getNoteUid());
        }
        stmt.bindLong(2, value.getLabelId());
      }
    };
  }

  @Override
  public Object addNoteAndLabel(final NoteAndLabel noteAndLabel,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNoteAndLabel.insert(noteAndLabel);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteNoteAndLabel(final NoteAndLabel noteAndLabel,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNoteAndLabel.handle(noteAndLabel);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<NoteAndLabel>> getAllNotesAndLabels() {
    final String _sql = "select * from note_and_label";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"note_and_label"}, new Callable<List<NoteAndLabel>>() {
      @Override
      public List<NoteAndLabel> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoteUid = CursorUtil.getColumnIndexOrThrow(_cursor, "noteUid");
          final int _cursorIndexOfLabelId = CursorUtil.getColumnIndexOrThrow(_cursor, "labelId");
          final List<NoteAndLabel> _result = new ArrayList<NoteAndLabel>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NoteAndLabel _item;
            final String _tmpNoteUid;
            if (_cursor.isNull(_cursorIndexOfNoteUid)) {
              _tmpNoteUid = null;
            } else {
              _tmpNoteUid = _cursor.getString(_cursorIndexOfNoteUid);
            }
            final long _tmpLabelId;
            _tmpLabelId = _cursor.getLong(_cursorIndexOfLabelId);
            _item = new NoteAndLabel(_tmpNoteUid,_tmpLabelId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
