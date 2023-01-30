package com.example.local.daos;

import android.database.Cursor;
import androidx.collection.ArrayMap;
import androidx.room.CoroutinesRoom;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.StringUtil;
import com.example.local.model.Entity;
import com.example.local.model.Label;
import com.example.local.model.Note;
import com.example.local.model.Todo;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Override;
import java.lang.String;
import java.lang.StringBuilder;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import kotlinx.coroutines.flow.Flow;

@SuppressWarnings({"unchecked", "deprecation"})
public final class EntityDao_Impl implements EntityDao {
  private final RoomDatabase __db;

  public EntityDao_Impl(RoomDatabase __db) {
    this.__db = __db;
  }

  @Override
  public Flow<List<Entity>> allEntitiesOrderedById() {
    final String _sql = "select * from NOTES where Trashed = 0 order by Date asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"note_and_label","label","note_and_todo","todo","NOTES"}, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "Uid");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
            final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "Priority");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
            final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "TextColor");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
            final int _cursorIndexOfTrashed = CursorUtil.getColumnIndexOrThrow(_cursor, "Trashed");
            final int _cursorIndexOfAudioDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioDuration");
            final int _cursorIndexOfReminding = CursorUtil.getColumnIndexOrThrow(_cursor, "Reminding");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "ImageUrl");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioUrl");
            final ArrayMap<String, ArrayList<Label>> _collectionLabels = new ArrayMap<String, ArrayList<Label>>();
            final ArrayMap<String, ArrayList<Todo>> _collectionTodoItems = new ArrayMap<String, ArrayList<Todo>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Label> _tmpLabelsCollection = _collectionLabels.get(_tmpKey);
              if (_tmpLabelsCollection == null) {
                _tmpLabelsCollection = new ArrayList<Label>();
                _collectionLabels.put(_tmpKey, _tmpLabelsCollection);
              }
              final String _tmpKey_1 = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Todo> _tmpTodoItemsCollection = _collectionTodoItems.get(_tmpKey_1);
              if (_tmpTodoItemsCollection == null) {
                _tmpTodoItemsCollection = new ArrayList<Todo>();
                _collectionTodoItems.put(_tmpKey_1, _tmpTodoItemsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiplabelAscomExampleLocalModelLabel(_collectionLabels);
            __fetchRelationshiptodoAscomExampleLocalModelTodo(_collectionTodoItems);
            final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final Entity _item;
              final Note _tmpNote;
              final String _tmpUid;
              if (_cursor.isNull(_cursorIndexOfUid)) {
                _tmpUid = null;
              } else {
                _tmpUid = _cursor.getString(_cursorIndexOfUid);
              }
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              final String _tmpPriority;
              if (_cursor.isNull(_cursorIndexOfPriority)) {
                _tmpPriority = null;
              } else {
                _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
              }
              final int _tmpColor;
              _tmpColor = _cursor.getInt(_cursorIndexOfColor);
              final int _tmpTextColor;
              _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
              final String _tmpDate;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmpDate = null;
              } else {
                _tmpDate = _cursor.getString(_cursorIndexOfDate);
              }
              final int _tmpTrashed;
              _tmpTrashed = _cursor.getInt(_cursorIndexOfTrashed);
              final int _tmpAudioDuration;
              _tmpAudioDuration = _cursor.getInt(_cursorIndexOfAudioDuration);
              final long _tmpReminding;
              _tmpReminding = _cursor.getLong(_cursorIndexOfReminding);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              final String _tmpAudioUrl;
              if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
                _tmpAudioUrl = null;
              } else {
                _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
              }
              _tmpNote = new Note(_tmpUid,_tmpTitle,_tmpDescription,_tmpPriority,_tmpColor,_tmpTextColor,_tmpDate,_tmpTrashed,_tmpAudioDuration,_tmpReminding,_tmpImageUrl,_tmpAudioUrl);
              ArrayList<Label> _tmpLabelsCollection_1 = null;
              final String _tmpKey_2 = _cursor.getString(_cursorIndexOfUid);
              _tmpLabelsCollection_1 = _collectionLabels.get(_tmpKey_2);
              if (_tmpLabelsCollection_1 == null) {
                _tmpLabelsCollection_1 = new ArrayList<Label>();
              }
              ArrayList<Todo> _tmpTodoItemsCollection_1 = null;
              final String _tmpKey_3 = _cursor.getString(_cursorIndexOfUid);
              _tmpTodoItemsCollection_1 = _collectionTodoItems.get(_tmpKey_3);
              if (_tmpTodoItemsCollection_1 == null) {
                _tmpTodoItemsCollection_1 = new ArrayList<Todo>();
              }
              _item = new Entity(_tmpNote,_tmpLabelsCollection_1,_tmpTodoItemsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Entity>> allEntitiesOrderedByNewest() {
    final String _sql = "select * from NOTES where Trashed = 0 order by Date desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"note_and_label","label","note_and_todo","todo","NOTES"}, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "Uid");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
            final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "Priority");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
            final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "TextColor");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
            final int _cursorIndexOfTrashed = CursorUtil.getColumnIndexOrThrow(_cursor, "Trashed");
            final int _cursorIndexOfAudioDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioDuration");
            final int _cursorIndexOfReminding = CursorUtil.getColumnIndexOrThrow(_cursor, "Reminding");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "ImageUrl");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioUrl");
            final ArrayMap<String, ArrayList<Label>> _collectionLabels = new ArrayMap<String, ArrayList<Label>>();
            final ArrayMap<String, ArrayList<Todo>> _collectionTodoItems = new ArrayMap<String, ArrayList<Todo>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Label> _tmpLabelsCollection = _collectionLabels.get(_tmpKey);
              if (_tmpLabelsCollection == null) {
                _tmpLabelsCollection = new ArrayList<Label>();
                _collectionLabels.put(_tmpKey, _tmpLabelsCollection);
              }
              final String _tmpKey_1 = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Todo> _tmpTodoItemsCollection = _collectionTodoItems.get(_tmpKey_1);
              if (_tmpTodoItemsCollection == null) {
                _tmpTodoItemsCollection = new ArrayList<Todo>();
                _collectionTodoItems.put(_tmpKey_1, _tmpTodoItemsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiplabelAscomExampleLocalModelLabel(_collectionLabels);
            __fetchRelationshiptodoAscomExampleLocalModelTodo(_collectionTodoItems);
            final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final Entity _item;
              final Note _tmpNote;
              final String _tmpUid;
              if (_cursor.isNull(_cursorIndexOfUid)) {
                _tmpUid = null;
              } else {
                _tmpUid = _cursor.getString(_cursorIndexOfUid);
              }
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              final String _tmpPriority;
              if (_cursor.isNull(_cursorIndexOfPriority)) {
                _tmpPriority = null;
              } else {
                _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
              }
              final int _tmpColor;
              _tmpColor = _cursor.getInt(_cursorIndexOfColor);
              final int _tmpTextColor;
              _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
              final String _tmpDate;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmpDate = null;
              } else {
                _tmpDate = _cursor.getString(_cursorIndexOfDate);
              }
              final int _tmpTrashed;
              _tmpTrashed = _cursor.getInt(_cursorIndexOfTrashed);
              final int _tmpAudioDuration;
              _tmpAudioDuration = _cursor.getInt(_cursorIndexOfAudioDuration);
              final long _tmpReminding;
              _tmpReminding = _cursor.getLong(_cursorIndexOfReminding);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              final String _tmpAudioUrl;
              if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
                _tmpAudioUrl = null;
              } else {
                _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
              }
              _tmpNote = new Note(_tmpUid,_tmpTitle,_tmpDescription,_tmpPriority,_tmpColor,_tmpTextColor,_tmpDate,_tmpTrashed,_tmpAudioDuration,_tmpReminding,_tmpImageUrl,_tmpAudioUrl);
              ArrayList<Label> _tmpLabelsCollection_1 = null;
              final String _tmpKey_2 = _cursor.getString(_cursorIndexOfUid);
              _tmpLabelsCollection_1 = _collectionLabels.get(_tmpKey_2);
              if (_tmpLabelsCollection_1 == null) {
                _tmpLabelsCollection_1 = new ArrayList<Label>();
              }
              ArrayList<Todo> _tmpTodoItemsCollection_1 = null;
              final String _tmpKey_3 = _cursor.getString(_cursorIndexOfUid);
              _tmpTodoItemsCollection_1 = _collectionTodoItems.get(_tmpKey_3);
              if (_tmpTodoItemsCollection_1 == null) {
                _tmpTodoItemsCollection_1 = new ArrayList<Todo>();
              }
              _item = new Entity(_tmpNote,_tmpLabelsCollection_1,_tmpTodoItemsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Entity>> allEntitiesOrderedByOldest() {
    final String _sql = "select * from NOTES where Trashed = 0 order by Date asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"note_and_label","label","note_and_todo","todo","NOTES"}, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "Uid");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
            final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "Priority");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
            final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "TextColor");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
            final int _cursorIndexOfTrashed = CursorUtil.getColumnIndexOrThrow(_cursor, "Trashed");
            final int _cursorIndexOfAudioDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioDuration");
            final int _cursorIndexOfReminding = CursorUtil.getColumnIndexOrThrow(_cursor, "Reminding");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "ImageUrl");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioUrl");
            final ArrayMap<String, ArrayList<Label>> _collectionLabels = new ArrayMap<String, ArrayList<Label>>();
            final ArrayMap<String, ArrayList<Todo>> _collectionTodoItems = new ArrayMap<String, ArrayList<Todo>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Label> _tmpLabelsCollection = _collectionLabels.get(_tmpKey);
              if (_tmpLabelsCollection == null) {
                _tmpLabelsCollection = new ArrayList<Label>();
                _collectionLabels.put(_tmpKey, _tmpLabelsCollection);
              }
              final String _tmpKey_1 = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Todo> _tmpTodoItemsCollection = _collectionTodoItems.get(_tmpKey_1);
              if (_tmpTodoItemsCollection == null) {
                _tmpTodoItemsCollection = new ArrayList<Todo>();
                _collectionTodoItems.put(_tmpKey_1, _tmpTodoItemsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiplabelAscomExampleLocalModelLabel(_collectionLabels);
            __fetchRelationshiptodoAscomExampleLocalModelTodo(_collectionTodoItems);
            final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final Entity _item;
              final Note _tmpNote;
              final String _tmpUid;
              if (_cursor.isNull(_cursorIndexOfUid)) {
                _tmpUid = null;
              } else {
                _tmpUid = _cursor.getString(_cursorIndexOfUid);
              }
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              final String _tmpPriority;
              if (_cursor.isNull(_cursorIndexOfPriority)) {
                _tmpPriority = null;
              } else {
                _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
              }
              final int _tmpColor;
              _tmpColor = _cursor.getInt(_cursorIndexOfColor);
              final int _tmpTextColor;
              _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
              final String _tmpDate;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmpDate = null;
              } else {
                _tmpDate = _cursor.getString(_cursorIndexOfDate);
              }
              final int _tmpTrashed;
              _tmpTrashed = _cursor.getInt(_cursorIndexOfTrashed);
              final int _tmpAudioDuration;
              _tmpAudioDuration = _cursor.getInt(_cursorIndexOfAudioDuration);
              final long _tmpReminding;
              _tmpReminding = _cursor.getLong(_cursorIndexOfReminding);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              final String _tmpAudioUrl;
              if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
                _tmpAudioUrl = null;
              } else {
                _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
              }
              _tmpNote = new Note(_tmpUid,_tmpTitle,_tmpDescription,_tmpPriority,_tmpColor,_tmpTextColor,_tmpDate,_tmpTrashed,_tmpAudioDuration,_tmpReminding,_tmpImageUrl,_tmpAudioUrl);
              ArrayList<Label> _tmpLabelsCollection_1 = null;
              final String _tmpKey_2 = _cursor.getString(_cursorIndexOfUid);
              _tmpLabelsCollection_1 = _collectionLabels.get(_tmpKey_2);
              if (_tmpLabelsCollection_1 == null) {
                _tmpLabelsCollection_1 = new ArrayList<Label>();
              }
              ArrayList<Todo> _tmpTodoItemsCollection_1 = null;
              final String _tmpKey_3 = _cursor.getString(_cursorIndexOfUid);
              _tmpTodoItemsCollection_1 = _collectionTodoItems.get(_tmpKey_3);
              if (_tmpTodoItemsCollection_1 == null) {
                _tmpTodoItemsCollection_1 = new ArrayList<Todo>();
              }
              _item = new Entity(_tmpNote,_tmpLabelsCollection_1,_tmpTodoItemsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Entity>> allEntitiesOrderedByName() {
    final String _sql = "select * from NOTES where Trashed = 0 order by TITLE asc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"note_and_label","label","note_and_todo","todo","NOTES"}, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "Uid");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
            final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "Priority");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
            final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "TextColor");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
            final int _cursorIndexOfTrashed = CursorUtil.getColumnIndexOrThrow(_cursor, "Trashed");
            final int _cursorIndexOfAudioDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioDuration");
            final int _cursorIndexOfReminding = CursorUtil.getColumnIndexOrThrow(_cursor, "Reminding");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "ImageUrl");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioUrl");
            final ArrayMap<String, ArrayList<Label>> _collectionLabels = new ArrayMap<String, ArrayList<Label>>();
            final ArrayMap<String, ArrayList<Todo>> _collectionTodoItems = new ArrayMap<String, ArrayList<Todo>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Label> _tmpLabelsCollection = _collectionLabels.get(_tmpKey);
              if (_tmpLabelsCollection == null) {
                _tmpLabelsCollection = new ArrayList<Label>();
                _collectionLabels.put(_tmpKey, _tmpLabelsCollection);
              }
              final String _tmpKey_1 = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Todo> _tmpTodoItemsCollection = _collectionTodoItems.get(_tmpKey_1);
              if (_tmpTodoItemsCollection == null) {
                _tmpTodoItemsCollection = new ArrayList<Todo>();
                _collectionTodoItems.put(_tmpKey_1, _tmpTodoItemsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiplabelAscomExampleLocalModelLabel(_collectionLabels);
            __fetchRelationshiptodoAscomExampleLocalModelTodo(_collectionTodoItems);
            final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final Entity _item;
              final Note _tmpNote;
              final String _tmpUid;
              if (_cursor.isNull(_cursorIndexOfUid)) {
                _tmpUid = null;
              } else {
                _tmpUid = _cursor.getString(_cursorIndexOfUid);
              }
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              final String _tmpPriority;
              if (_cursor.isNull(_cursorIndexOfPriority)) {
                _tmpPriority = null;
              } else {
                _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
              }
              final int _tmpColor;
              _tmpColor = _cursor.getInt(_cursorIndexOfColor);
              final int _tmpTextColor;
              _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
              final String _tmpDate;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmpDate = null;
              } else {
                _tmpDate = _cursor.getString(_cursorIndexOfDate);
              }
              final int _tmpTrashed;
              _tmpTrashed = _cursor.getInt(_cursorIndexOfTrashed);
              final int _tmpAudioDuration;
              _tmpAudioDuration = _cursor.getInt(_cursorIndexOfAudioDuration);
              final long _tmpReminding;
              _tmpReminding = _cursor.getLong(_cursorIndexOfReminding);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              final String _tmpAudioUrl;
              if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
                _tmpAudioUrl = null;
              } else {
                _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
              }
              _tmpNote = new Note(_tmpUid,_tmpTitle,_tmpDescription,_tmpPriority,_tmpColor,_tmpTextColor,_tmpDate,_tmpTrashed,_tmpAudioDuration,_tmpReminding,_tmpImageUrl,_tmpAudioUrl);
              ArrayList<Label> _tmpLabelsCollection_1 = null;
              final String _tmpKey_2 = _cursor.getString(_cursorIndexOfUid);
              _tmpLabelsCollection_1 = _collectionLabels.get(_tmpKey_2);
              if (_tmpLabelsCollection_1 == null) {
                _tmpLabelsCollection_1 = new ArrayList<Label>();
              }
              ArrayList<Todo> _tmpTodoItemsCollection_1 = null;
              final String _tmpKey_3 = _cursor.getString(_cursorIndexOfUid);
              _tmpTodoItemsCollection_1 = _collectionTodoItems.get(_tmpKey_3);
              if (_tmpTodoItemsCollection_1 == null) {
                _tmpTodoItemsCollection_1 = new ArrayList<Todo>();
              }
              _item = new Entity(_tmpNote,_tmpLabelsCollection_1,_tmpTodoItemsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Entity>> allEntitiesOrderedByPriority() {
    final String _sql = "select * from NOTES where Trashed = 0 order by case when Priority like 'URGENT' then 1 when Priority like 'IMPORTANT' then 2 when Priority like 'NORMAL' then 3 when Priority like 'LOW' then 4 when Priority like 'NON' then 5 end";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"note_and_label","label","note_and_todo","todo","NOTES"}, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "Uid");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
            final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "Priority");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
            final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "TextColor");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
            final int _cursorIndexOfTrashed = CursorUtil.getColumnIndexOrThrow(_cursor, "Trashed");
            final int _cursorIndexOfAudioDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioDuration");
            final int _cursorIndexOfReminding = CursorUtil.getColumnIndexOrThrow(_cursor, "Reminding");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "ImageUrl");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioUrl");
            final ArrayMap<String, ArrayList<Label>> _collectionLabels = new ArrayMap<String, ArrayList<Label>>();
            final ArrayMap<String, ArrayList<Todo>> _collectionTodoItems = new ArrayMap<String, ArrayList<Todo>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Label> _tmpLabelsCollection = _collectionLabels.get(_tmpKey);
              if (_tmpLabelsCollection == null) {
                _tmpLabelsCollection = new ArrayList<Label>();
                _collectionLabels.put(_tmpKey, _tmpLabelsCollection);
              }
              final String _tmpKey_1 = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Todo> _tmpTodoItemsCollection = _collectionTodoItems.get(_tmpKey_1);
              if (_tmpTodoItemsCollection == null) {
                _tmpTodoItemsCollection = new ArrayList<Todo>();
                _collectionTodoItems.put(_tmpKey_1, _tmpTodoItemsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiplabelAscomExampleLocalModelLabel(_collectionLabels);
            __fetchRelationshiptodoAscomExampleLocalModelTodo(_collectionTodoItems);
            final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final Entity _item;
              final Note _tmpNote;
              final String _tmpUid;
              if (_cursor.isNull(_cursorIndexOfUid)) {
                _tmpUid = null;
              } else {
                _tmpUid = _cursor.getString(_cursorIndexOfUid);
              }
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              final String _tmpPriority;
              if (_cursor.isNull(_cursorIndexOfPriority)) {
                _tmpPriority = null;
              } else {
                _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
              }
              final int _tmpColor;
              _tmpColor = _cursor.getInt(_cursorIndexOfColor);
              final int _tmpTextColor;
              _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
              final String _tmpDate;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmpDate = null;
              } else {
                _tmpDate = _cursor.getString(_cursorIndexOfDate);
              }
              final int _tmpTrashed;
              _tmpTrashed = _cursor.getInt(_cursorIndexOfTrashed);
              final int _tmpAudioDuration;
              _tmpAudioDuration = _cursor.getInt(_cursorIndexOfAudioDuration);
              final long _tmpReminding;
              _tmpReminding = _cursor.getLong(_cursorIndexOfReminding);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              final String _tmpAudioUrl;
              if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
                _tmpAudioUrl = null;
              } else {
                _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
              }
              _tmpNote = new Note(_tmpUid,_tmpTitle,_tmpDescription,_tmpPriority,_tmpColor,_tmpTextColor,_tmpDate,_tmpTrashed,_tmpAudioDuration,_tmpReminding,_tmpImageUrl,_tmpAudioUrl);
              ArrayList<Label> _tmpLabelsCollection_1 = null;
              final String _tmpKey_2 = _cursor.getString(_cursorIndexOfUid);
              _tmpLabelsCollection_1 = _collectionLabels.get(_tmpKey_2);
              if (_tmpLabelsCollection_1 == null) {
                _tmpLabelsCollection_1 = new ArrayList<Label>();
              }
              ArrayList<Todo> _tmpTodoItemsCollection_1 = null;
              final String _tmpKey_3 = _cursor.getString(_cursorIndexOfUid);
              _tmpTodoItemsCollection_1 = _collectionTodoItems.get(_tmpKey_3);
              if (_tmpTodoItemsCollection_1 == null) {
                _tmpTodoItemsCollection_1 = new ArrayList<Todo>();
              }
              _item = new Entity(_tmpNote,_tmpLabelsCollection_1,_tmpTodoItemsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Entity>> allTrashedNotes() {
    final String _sql = "select * from NOTES where Trashed = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"note_and_label","label","note_and_todo","todo","NOTES"}, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "Uid");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
            final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "Priority");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
            final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "TextColor");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
            final int _cursorIndexOfTrashed = CursorUtil.getColumnIndexOrThrow(_cursor, "Trashed");
            final int _cursorIndexOfAudioDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioDuration");
            final int _cursorIndexOfReminding = CursorUtil.getColumnIndexOrThrow(_cursor, "Reminding");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "ImageUrl");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioUrl");
            final ArrayMap<String, ArrayList<Label>> _collectionLabels = new ArrayMap<String, ArrayList<Label>>();
            final ArrayMap<String, ArrayList<Todo>> _collectionTodoItems = new ArrayMap<String, ArrayList<Todo>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Label> _tmpLabelsCollection = _collectionLabels.get(_tmpKey);
              if (_tmpLabelsCollection == null) {
                _tmpLabelsCollection = new ArrayList<Label>();
                _collectionLabels.put(_tmpKey, _tmpLabelsCollection);
              }
              final String _tmpKey_1 = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Todo> _tmpTodoItemsCollection = _collectionTodoItems.get(_tmpKey_1);
              if (_tmpTodoItemsCollection == null) {
                _tmpTodoItemsCollection = new ArrayList<Todo>();
                _collectionTodoItems.put(_tmpKey_1, _tmpTodoItemsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiplabelAscomExampleLocalModelLabel(_collectionLabels);
            __fetchRelationshiptodoAscomExampleLocalModelTodo(_collectionTodoItems);
            final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final Entity _item;
              final Note _tmpNote;
              final String _tmpUid;
              if (_cursor.isNull(_cursorIndexOfUid)) {
                _tmpUid = null;
              } else {
                _tmpUid = _cursor.getString(_cursorIndexOfUid);
              }
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              final String _tmpPriority;
              if (_cursor.isNull(_cursorIndexOfPriority)) {
                _tmpPriority = null;
              } else {
                _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
              }
              final int _tmpColor;
              _tmpColor = _cursor.getInt(_cursorIndexOfColor);
              final int _tmpTextColor;
              _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
              final String _tmpDate;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmpDate = null;
              } else {
                _tmpDate = _cursor.getString(_cursorIndexOfDate);
              }
              final int _tmpTrashed;
              _tmpTrashed = _cursor.getInt(_cursorIndexOfTrashed);
              final int _tmpAudioDuration;
              _tmpAudioDuration = _cursor.getInt(_cursorIndexOfAudioDuration);
              final long _tmpReminding;
              _tmpReminding = _cursor.getLong(_cursorIndexOfReminding);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              final String _tmpAudioUrl;
              if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
                _tmpAudioUrl = null;
              } else {
                _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
              }
              _tmpNote = new Note(_tmpUid,_tmpTitle,_tmpDescription,_tmpPriority,_tmpColor,_tmpTextColor,_tmpDate,_tmpTrashed,_tmpAudioDuration,_tmpReminding,_tmpImageUrl,_tmpAudioUrl);
              ArrayList<Label> _tmpLabelsCollection_1 = null;
              final String _tmpKey_2 = _cursor.getString(_cursorIndexOfUid);
              _tmpLabelsCollection_1 = _collectionLabels.get(_tmpKey_2);
              if (_tmpLabelsCollection_1 == null) {
                _tmpLabelsCollection_1 = new ArrayList<Label>();
              }
              ArrayList<Todo> _tmpTodoItemsCollection_1 = null;
              final String _tmpKey_3 = _cursor.getString(_cursorIndexOfUid);
              _tmpTodoItemsCollection_1 = _collectionTodoItems.get(_tmpKey_3);
              if (_tmpTodoItemsCollection_1 == null) {
                _tmpTodoItemsCollection_1 = new ArrayList<Todo>();
              }
              _item = new Entity(_tmpNote,_tmpLabelsCollection_1,_tmpTodoItemsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<Entity>> allRemindingNotes() {
    final String _sql = "select * from NOTES where Trashed = 0 order by Reminding desc";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, true, new String[]{"note_and_label","label","note_and_todo","todo","NOTES"}, new Callable<List<Entity>>() {
      @Override
      public List<Entity> call() throws Exception {
        __db.beginTransaction();
        try {
          final Cursor _cursor = DBUtil.query(__db, _statement, true, null);
          try {
            final int _cursorIndexOfUid = CursorUtil.getColumnIndexOrThrow(_cursor, "Uid");
            final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "Title");
            final int _cursorIndexOfDescription = CursorUtil.getColumnIndexOrThrow(_cursor, "Description");
            final int _cursorIndexOfPriority = CursorUtil.getColumnIndexOrThrow(_cursor, "Priority");
            final int _cursorIndexOfColor = CursorUtil.getColumnIndexOrThrow(_cursor, "Color");
            final int _cursorIndexOfTextColor = CursorUtil.getColumnIndexOrThrow(_cursor, "TextColor");
            final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "Date");
            final int _cursorIndexOfTrashed = CursorUtil.getColumnIndexOrThrow(_cursor, "Trashed");
            final int _cursorIndexOfAudioDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioDuration");
            final int _cursorIndexOfReminding = CursorUtil.getColumnIndexOrThrow(_cursor, "Reminding");
            final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "ImageUrl");
            final int _cursorIndexOfAudioUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "AudioUrl");
            final ArrayMap<String, ArrayList<Label>> _collectionLabels = new ArrayMap<String, ArrayList<Label>>();
            final ArrayMap<String, ArrayList<Todo>> _collectionTodoItems = new ArrayMap<String, ArrayList<Todo>>();
            while (_cursor.moveToNext()) {
              final String _tmpKey = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Label> _tmpLabelsCollection = _collectionLabels.get(_tmpKey);
              if (_tmpLabelsCollection == null) {
                _tmpLabelsCollection = new ArrayList<Label>();
                _collectionLabels.put(_tmpKey, _tmpLabelsCollection);
              }
              final String _tmpKey_1 = _cursor.getString(_cursorIndexOfUid);
              ArrayList<Todo> _tmpTodoItemsCollection = _collectionTodoItems.get(_tmpKey_1);
              if (_tmpTodoItemsCollection == null) {
                _tmpTodoItemsCollection = new ArrayList<Todo>();
                _collectionTodoItems.put(_tmpKey_1, _tmpTodoItemsCollection);
              }
            }
            _cursor.moveToPosition(-1);
            __fetchRelationshiplabelAscomExampleLocalModelLabel(_collectionLabels);
            __fetchRelationshiptodoAscomExampleLocalModelTodo(_collectionTodoItems);
            final List<Entity> _result = new ArrayList<Entity>(_cursor.getCount());
            while(_cursor.moveToNext()) {
              final Entity _item;
              final Note _tmpNote;
              final String _tmpUid;
              if (_cursor.isNull(_cursorIndexOfUid)) {
                _tmpUid = null;
              } else {
                _tmpUid = _cursor.getString(_cursorIndexOfUid);
              }
              final String _tmpTitle;
              if (_cursor.isNull(_cursorIndexOfTitle)) {
                _tmpTitle = null;
              } else {
                _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
              }
              final String _tmpDescription;
              if (_cursor.isNull(_cursorIndexOfDescription)) {
                _tmpDescription = null;
              } else {
                _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
              }
              final String _tmpPriority;
              if (_cursor.isNull(_cursorIndexOfPriority)) {
                _tmpPriority = null;
              } else {
                _tmpPriority = _cursor.getString(_cursorIndexOfPriority);
              }
              final int _tmpColor;
              _tmpColor = _cursor.getInt(_cursorIndexOfColor);
              final int _tmpTextColor;
              _tmpTextColor = _cursor.getInt(_cursorIndexOfTextColor);
              final String _tmpDate;
              if (_cursor.isNull(_cursorIndexOfDate)) {
                _tmpDate = null;
              } else {
                _tmpDate = _cursor.getString(_cursorIndexOfDate);
              }
              final int _tmpTrashed;
              _tmpTrashed = _cursor.getInt(_cursorIndexOfTrashed);
              final int _tmpAudioDuration;
              _tmpAudioDuration = _cursor.getInt(_cursorIndexOfAudioDuration);
              final long _tmpReminding;
              _tmpReminding = _cursor.getLong(_cursorIndexOfReminding);
              final String _tmpImageUrl;
              if (_cursor.isNull(_cursorIndexOfImageUrl)) {
                _tmpImageUrl = null;
              } else {
                _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
              }
              final String _tmpAudioUrl;
              if (_cursor.isNull(_cursorIndexOfAudioUrl)) {
                _tmpAudioUrl = null;
              } else {
                _tmpAudioUrl = _cursor.getString(_cursorIndexOfAudioUrl);
              }
              _tmpNote = new Note(_tmpUid,_tmpTitle,_tmpDescription,_tmpPriority,_tmpColor,_tmpTextColor,_tmpDate,_tmpTrashed,_tmpAudioDuration,_tmpReminding,_tmpImageUrl,_tmpAudioUrl);
              ArrayList<Label> _tmpLabelsCollection_1 = null;
              final String _tmpKey_2 = _cursor.getString(_cursorIndexOfUid);
              _tmpLabelsCollection_1 = _collectionLabels.get(_tmpKey_2);
              if (_tmpLabelsCollection_1 == null) {
                _tmpLabelsCollection_1 = new ArrayList<Label>();
              }
              ArrayList<Todo> _tmpTodoItemsCollection_1 = null;
              final String _tmpKey_3 = _cursor.getString(_cursorIndexOfUid);
              _tmpTodoItemsCollection_1 = _collectionTodoItems.get(_tmpKey_3);
              if (_tmpTodoItemsCollection_1 == null) {
                _tmpTodoItemsCollection_1 = new ArrayList<Todo>();
              }
              _item = new Entity(_tmpNote,_tmpLabelsCollection_1,_tmpTodoItemsCollection_1);
              _result.add(_item);
            }
            __db.setTransactionSuccessful();
            return _result;
          } finally {
            _cursor.close();
          }
        } finally {
          __db.endTransaction();
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

  private void __fetchRelationshiplabelAscomExampleLocalModelLabel(
      final ArrayMap<String, ArrayList<Label>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      ArrayMap<String, ArrayList<Label>> _tmpInnerMap = new ArrayMap<String, ArrayList<Label>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiplabelAscomExampleLocalModelLabel(_tmpInnerMap);
          _tmpInnerMap = new ArrayMap<String, ArrayList<Label>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiplabelAscomExampleLocalModelLabel(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `label`.`Id` AS `Id`,`label`.`label` AS `label`,`label`.`Color` AS `Color`,_junction.`noteUid` FROM `note_and_label` AS _junction INNER JOIN `label` ON (_junction.`labelId` = `label`.`Id`) WHERE _junction.`noteUid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 3; // _junction.noteUid;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfLabel = 1;
      final int _cursorIndexOfColor = 2;
      while(_cursor.moveToNext()) {
        final String _tmpKey = _cursor.getString(_itemKeyIndex);
        ArrayList<Label> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Label _item_1;
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
          _item_1 = new Label(_tmpId,_tmpLabel,_tmpColor);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }

  private void __fetchRelationshiptodoAscomExampleLocalModelTodo(
      final ArrayMap<String, ArrayList<Todo>> _map) {
    final Set<String> __mapKeySet = _map.keySet();
    if (__mapKeySet.isEmpty()) {
      return;
    }
    // check if the size is too big, if so divide;
    if(_map.size() > RoomDatabase.MAX_BIND_PARAMETER_CNT) {
      ArrayMap<String, ArrayList<Todo>> _tmpInnerMap = new ArrayMap<String, ArrayList<Todo>>(androidx.room.RoomDatabase.MAX_BIND_PARAMETER_CNT);
      int _tmpIndex = 0;
      int _mapIndex = 0;
      final int _limit = _map.size();
      while(_mapIndex < _limit) {
        _tmpInnerMap.put(_map.keyAt(_mapIndex), _map.valueAt(_mapIndex));
        _mapIndex++;
        _tmpIndex++;
        if(_tmpIndex == RoomDatabase.MAX_BIND_PARAMETER_CNT) {
          __fetchRelationshiptodoAscomExampleLocalModelTodo(_tmpInnerMap);
          _tmpInnerMap = new ArrayMap<String, ArrayList<Todo>>(RoomDatabase.MAX_BIND_PARAMETER_CNT);
          _tmpIndex = 0;
        }
      }
      if(_tmpIndex > 0) {
        __fetchRelationshiptodoAscomExampleLocalModelTodo(_tmpInnerMap);
      }
      return;
    }
    StringBuilder _stringBuilder = StringUtil.newStringBuilder();
    _stringBuilder.append("SELECT `todo`.`Id` AS `Id`,`todo`.`item` AS `item`,`todo`.`isDone` AS `isDone`,_junction.`noteUid` FROM `note_and_todo` AS _junction INNER JOIN `todo` ON (_junction.`todoId` = `todo`.`Id`) WHERE _junction.`noteUid` IN (");
    final int _inputSize = __mapKeySet.size();
    StringUtil.appendPlaceholders(_stringBuilder, _inputSize);
    _stringBuilder.append(")");
    final String _sql = _stringBuilder.toString();
    final int _argCount = 0 + _inputSize;
    final RoomSQLiteQuery _stmt = RoomSQLiteQuery.acquire(_sql, _argCount);
    int _argIndex = 1;
    for (String _item : __mapKeySet) {
      if (_item == null) {
        _stmt.bindNull(_argIndex);
      } else {
        _stmt.bindString(_argIndex, _item);
      }
      _argIndex ++;
    }
    final Cursor _cursor = DBUtil.query(__db, _stmt, false, null);
    try {
      final int _itemKeyIndex = 3; // _junction.noteUid;
      if (_itemKeyIndex == -1) {
        return;
      }
      final int _cursorIndexOfId = 0;
      final int _cursorIndexOfItem = 1;
      final int _cursorIndexOfIsDone = 2;
      while(_cursor.moveToNext()) {
        final String _tmpKey = _cursor.getString(_itemKeyIndex);
        ArrayList<Todo> _tmpRelation = _map.get(_tmpKey);
        if (_tmpRelation != null) {
          final Todo _item_1;
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
          _item_1 = new Todo(_tmpId,_tmpItem,_tmpIsDone);
          _tmpRelation.add(_item_1);
        }
      }
    } finally {
      _cursor.close();
    }
  }
}
