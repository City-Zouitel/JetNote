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
import com.example.local.model.Label;
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
public final class LabelDao_Impl implements LabelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Label> __insertionAdapterOfLabel;

  private final EntityDeletionOrUpdateAdapter<Label> __deletionAdapterOfLabel;

  private final EntityDeletionOrUpdateAdapter<Label> __updateAdapterOfLabel;

  public LabelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfLabel = new EntityInsertionAdapter<Label>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `label` (`Id`,`label`,`Color`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Label value) {
        stmt.bindLong(1, value.getId());
        if (value.getLabel() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLabel());
        }
        stmt.bindLong(3, value.getColor());
      }
    };
    this.__deletionAdapterOfLabel = new EntityDeletionOrUpdateAdapter<Label>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `label` WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Label value) {
        stmt.bindLong(1, value.getId());
      }
    };
    this.__updateAdapterOfLabel = new EntityDeletionOrUpdateAdapter<Label>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `label` SET `Id` = ?,`label` = ?,`Color` = ? WHERE `Id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Label value) {
        stmt.bindLong(1, value.getId());
        if (value.getLabel() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getLabel());
        }
        stmt.bindLong(3, value.getColor());
        stmt.bindLong(4, value.getId());
      }
    };
  }

  @Override
  public Object addLabel(final Label label, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfLabel.insert(label);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteLabel(final Label label, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfLabel.handle(label);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object updateLabel(final Label label, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfLabel.handle(label);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Flow<List<Label>> getAllLabels() {
    final String _sql = "select * from label";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[]{"label"}, new Callable<List<Label>>() {
      @Override
      public List<Label> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "Id");
          final int _cursorIndexOfLabel = CursorUtil.getColumnIndexOrThrow(_cursor, "label");
          final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
          final List<Label> _result = new ArrayList<Label>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final Label _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpLabel;
            if (_cursor.isNull(_cursorIndexOfLabel)) {
              _tmpLabel = null;
            } else {
              _tmpLabel = _cursor.getString(_cursorIndexOfLabel);
            }
            final int _tmpColor;
            _tmpColor = _cursor.getInt(_cursorIndexOfColor);
            _item = new Label(_tmpId,_tmpLabel,_tmpColor);
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
