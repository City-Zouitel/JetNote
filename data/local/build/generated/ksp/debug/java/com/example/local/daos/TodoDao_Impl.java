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
import com.example.local.model.Todo;
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
public final class TodoDao_Impl implements TodoDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Todo> __insertionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter<Todo> __deletionAdapterOfTodo;

  private final EntityDeletionOrUpdateAdapter<Todo> __updateAdapterOfTodo;

  public TodoDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTodo = new EntityInsertionAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `todo` (`Id`,`item`,`isDone`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.getId());
        if (value.getItem() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getItem());
        }
        final int _tmp = value.isDone() ? 1 : 0;
        stmt.bindLong(3, _tmp);
      }
    };
    this.__deletionAdapterOfTodo = new EntityDeletionOrUpdateAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `todo` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfTodo = new EntityDeletionOrUpdateAdapter<Todo>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `todo` SET `Id` = ?,`item` = ?,`isDone` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Todo value) {
        stmt.bindLong(1, value.getId());
        if (value.getItem() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getItem());
        }
        final int _tmp = value.isDone() ? 1 : 0;
        stmt.bindLong(3, _tmp);
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public Object addTodoItem(final Todo item, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfTodo.insert(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteTodoItem(final Todo item, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfTodo.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateTodoItem(final Todo item, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfTodo.handle(item);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Todo>> getAllTodoItems() {
    final String _sql = "select * from todo";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"todo"}, new Callable<List<Todo>>() {
      @Override
      public List<Todo> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "Id");
          final int _cursorIndexOfItem = CursorUtil.getColumnIndexOrThrow(_cursor, "item");
          final int _cursorIndexOfIsDone = CursorUtil.getColumnIndexOrThrow(_cursor, "isDone");
          final List<Todo> _result = new ArrayList<Todo>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Todo _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpItem;
            if (_cursor.isNull(_cursorIndexOfItem)) {
              _tmpItem = null;
            } else {
              _tmpItem = _cursor.getString(_cursorIndexOfItem);
            }
            final boolean _tmpIsDone;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsDone);
            _tmpIsDone = _tmp != 0;
            _item = new Todo(_tmpId,_tmpItem,_tmpIsDone);
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
