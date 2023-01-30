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
import com.example.local.model.NoteAndTodo;
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
public final class NoteAndTodoDao_Impl implements NoteAndTodoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<NoteAndTodo> __insertionAdapterOfNoteAndTodo;

  private final EntityDeletionOrUpdateAdapter<NoteAndTodo> __deletionAdapterOfNoteAndTodo;

  public NoteAndTodoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfNoteAndTodo = new EntityInsertionAdapter<NoteAndTodo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `note_and_todo` (`noteUid`,`todoId`) VALUES (?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteAndTodo value) {
        if (value.getNoteUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getNoteUid());
        }
        stmt.bindLong(2, value.getTodoId());
      }
    };
    this.__deletionAdapterOfNoteAndTodo = new EntityDeletionOrUpdateAdapter<NoteAndTodo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `note_and_todo` WHERE `noteUid` = ? AND `todoId` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, NoteAndTodo value) {
        if (value.getNoteUid() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getNoteUid());
        }
        stmt.bindLong(2, value.getTodoId());
      }
    };
  }

  @Override
  public Object addNoteAndTodo(final NoteAndTodo noteAndTodo,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfNoteAndTodo.insert(noteAndTodo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteNoteAndTodo(final NoteAndTodo noteAndTodo,
      final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfNoteAndTodo.handle(noteAndTodo);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<NoteAndTodo>> getAllNotesAndTodo() {
    final String _sql = "select * from note_and_todo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"note_and_todo"}, new Callable<List<NoteAndTodo>>() {
      @Override
      public List<NoteAndTodo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfNoteUid = CursorUtil.getColumnIndexOrThrow(_cursor, "noteUid");
          final int _cursorIndexOfTodoId = CursorUtil.getColumnIndexOrThrow(_cursor, "todoId");
          final List<NoteAndTodo> _result = new ArrayList<NoteAndTodo>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final NoteAndTodo _item;
            final String _tmpNoteUid;
            if (_cursor.isNull(_cursorIndexOfNoteUid)) {
              _tmpNoteUid = null;
            } else {
              _tmpNoteUid = _cursor.getString(_cursorIndexOfNoteUid);
            }
            final long _tmpTodoId;
            _tmpTodoId = _cursor.getLong(_cursorIndexOfTodoId);
            _item = new NoteAndTodo(_tmpNoteUid,_tmpTodoId);
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
