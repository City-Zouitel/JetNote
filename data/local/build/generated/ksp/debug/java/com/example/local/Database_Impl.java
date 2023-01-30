package com.example.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.example.local.daos.EntityDao;
import com.example.local.daos.EntityDao_Impl;
import com.example.local.daos.LabelDao;
import com.example.local.daos.LabelDao_Impl;
import com.example.local.daos.NoteAndLabelDao;
import com.example.local.daos.NoteAndLabelDao_Impl;
import com.example.local.daos.NoteAndTodoDao;
import com.example.local.daos.NoteAndTodoDao_Impl;
import com.example.local.daos.NotesDao;
import com.example.local.daos.NotesDao_Impl;
import com.example.local.daos.TodoDao;
import com.example.local.daos.TodoDao_Impl;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class Database_Impl extends Database {
  private volatile NotesDao _notesDao;

  private volatile LabelDao _labelDao;

  private volatile NoteAndLabelDao _noteAndLabelDao;

  private volatile EntityDao _entityDao;

  private volatile TodoDao _todoDao;

  private volatile NoteAndTodoDao _noteAndTodoDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `note_and_label` (`noteUid` TEXT NOT NULL, `labelId` INTEGER NOT NULL, PRIMARY KEY(`noteUid`, `labelId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `note_and_todo` (`noteUid` TEXT NOT NULL, `todoId` INTEGER NOT NULL, PRIMARY KEY(`noteUid`, `todoId`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `Notes` (`Uid` TEXT NOT NULL, `Title` TEXT, `Description` TEXT, `Priority` TEXT NOT NULL, `Color` INTEGER NOT NULL, `TextColor` INTEGER NOT NULL, `Date` TEXT NOT NULL, `Trashed` INTEGER NOT NULL, `AudioDuration` INTEGER NOT NULL, `Reminding` INTEGER NOT NULL, `ImageUrl` TEXT, `AudioUrl` TEXT, PRIMARY KEY(`Uid`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `label` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `label` TEXT, `Color` INTEGER NOT NULL DEFAULT 0x0000)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `todo` (`Id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `item` TEXT, `isDone` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '180ffcab54b9870c46438d593941e3f0')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `note_and_label`");
        _db.execSQL("DROP TABLE IF EXISTS `note_and_todo`");
        _db.execSQL("DROP TABLE IF EXISTS `Notes`");
        _db.execSQL("DROP TABLE IF EXISTS `label`");
        _db.execSQL("DROP TABLE IF EXISTS `todo`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      public void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsNoteAndLabel = new HashMap<String, TableInfo.Column>(2);
        _columnsNoteAndLabel.put("noteUid", new TableInfo.Column("noteUid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNoteAndLabel.put("labelId", new TableInfo.Column("labelId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNoteAndLabel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNoteAndLabel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNoteAndLabel = new TableInfo("note_and_label", _columnsNoteAndLabel, _foreignKeysNoteAndLabel, _indicesNoteAndLabel);
        final TableInfo _existingNoteAndLabel = TableInfo.read(_db, "note_and_label");
        if (! _infoNoteAndLabel.equals(_existingNoteAndLabel)) {
          return new RoomOpenHelper.ValidationResult(false, "note_and_label(com.example.local.model.NoteAndLabel).\n"
                  + " Expected:\n" + _infoNoteAndLabel + "\n"
                  + " Found:\n" + _existingNoteAndLabel);
        }
        final HashMap<String, TableInfo.Column> _columnsNoteAndTodo = new HashMap<String, TableInfo.Column>(2);
        _columnsNoteAndTodo.put("noteUid", new TableInfo.Column("noteUid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNoteAndTodo.put("todoId", new TableInfo.Column("todoId", "INTEGER", true, 2, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNoteAndTodo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNoteAndTodo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNoteAndTodo = new TableInfo("note_and_todo", _columnsNoteAndTodo, _foreignKeysNoteAndTodo, _indicesNoteAndTodo);
        final TableInfo _existingNoteAndTodo = TableInfo.read(_db, "note_and_todo");
        if (! _infoNoteAndTodo.equals(_existingNoteAndTodo)) {
          return new RoomOpenHelper.ValidationResult(false, "note_and_todo(com.example.local.model.NoteAndTodo).\n"
                  + " Expected:\n" + _infoNoteAndTodo + "\n"
                  + " Found:\n" + _existingNoteAndTodo);
        }
        final HashMap<String, TableInfo.Column> _columnsNotes = new HashMap<String, TableInfo.Column>(12);
        _columnsNotes.put("Uid", new TableInfo.Column("Uid", "TEXT", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("Title", new TableInfo.Column("Title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("Description", new TableInfo.Column("Description", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("Priority", new TableInfo.Column("Priority", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("Color", new TableInfo.Column("Color", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("TextColor", new TableInfo.Column("TextColor", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("Date", new TableInfo.Column("Date", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("Trashed", new TableInfo.Column("Trashed", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("AudioDuration", new TableInfo.Column("AudioDuration", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("Reminding", new TableInfo.Column("Reminding", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("ImageUrl", new TableInfo.Column("ImageUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsNotes.put("AudioUrl", new TableInfo.Column("AudioUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysNotes = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesNotes = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoNotes = new TableInfo("Notes", _columnsNotes, _foreignKeysNotes, _indicesNotes);
        final TableInfo _existingNotes = TableInfo.read(_db, "Notes");
        if (! _infoNotes.equals(_existingNotes)) {
          return new RoomOpenHelper.ValidationResult(false, "Notes(com.example.local.model.Note).\n"
                  + " Expected:\n" + _infoNotes + "\n"
                  + " Found:\n" + _existingNotes);
        }
        final HashMap<String, TableInfo.Column> _columnsLabel = new HashMap<String, TableInfo.Column>(3);
        _columnsLabel.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLabel.put("label", new TableInfo.Column("label", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLabel.put("Color", new TableInfo.Column("Color", "INTEGER", true, 0, "0x0000", TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLabel = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLabel = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLabel = new TableInfo("label", _columnsLabel, _foreignKeysLabel, _indicesLabel);
        final TableInfo _existingLabel = TableInfo.read(_db, "label");
        if (! _infoLabel.equals(_existingLabel)) {
          return new RoomOpenHelper.ValidationResult(false, "label(com.example.local.model.Label).\n"
                  + " Expected:\n" + _infoLabel + "\n"
                  + " Found:\n" + _existingLabel);
        }
        final HashMap<String, TableInfo.Column> _columnsTodo = new HashMap<String, TableInfo.Column>(3);
        _columnsTodo.put("Id", new TableInfo.Column("Id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTodo.put("item", new TableInfo.Column("item", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsTodo.put("isDone", new TableInfo.Column("isDone", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysTodo = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesTodo = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoTodo = new TableInfo("todo", _columnsTodo, _foreignKeysTodo, _indicesTodo);
        final TableInfo _existingTodo = TableInfo.read(_db, "todo");
        if (! _infoTodo.equals(_existingTodo)) {
          return new RoomOpenHelper.ValidationResult(false, "todo(com.example.local.model.Todo).\n"
                  + " Expected:\n" + _infoTodo + "\n"
                  + " Found:\n" + _existingTodo);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "180ffcab54b9870c46438d593941e3f0", "d60b4806da2567921677954b486424f9");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "note_and_label","note_and_todo","Notes","label","todo");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `note_and_label`");
      _db.execSQL("DELETE FROM `note_and_todo`");
      _db.execSQL("DELETE FROM `Notes`");
      _db.execSQL("DELETE FROM `label`");
      _db.execSQL("DELETE FROM `todo`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(NotesDao.class, NotesDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(LabelDao.class, LabelDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteAndLabelDao.class, NoteAndLabelDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(EntityDao.class, EntityDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(TodoDao.class, TodoDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(NoteAndTodoDao.class, NoteAndTodoDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public NotesDao getNoteDao() {
    if (_notesDao != null) {
      return _notesDao;
    } else {
      synchronized(this) {
        if(_notesDao == null) {
          _notesDao = new NotesDao_Impl(this);
        }
        return _notesDao;
      }
    }
  }

  @Override
  public LabelDao getLabelDao() {
    if (_labelDao != null) {
      return _labelDao;
    } else {
      synchronized(this) {
        if(_labelDao == null) {
          _labelDao = new LabelDao_Impl(this);
        }
        return _labelDao;
      }
    }
  }

  @Override
  public NoteAndLabelDao getNoteAndLabelDao() {
    if (_noteAndLabelDao != null) {
      return _noteAndLabelDao;
    } else {
      synchronized(this) {
        if(_noteAndLabelDao == null) {
          _noteAndLabelDao = new NoteAndLabelDao_Impl(this);
        }
        return _noteAndLabelDao;
      }
    }
  }

  @Override
  public EntityDao getEntityDao() {
    if (_entityDao != null) {
      return _entityDao;
    } else {
      synchronized(this) {
        if(_entityDao == null) {
          _entityDao = new EntityDao_Impl(this);
        }
        return _entityDao;
      }
    }
  }

  @Override
  public TodoDao getTodoDao() {
    if (_todoDao != null) {
      return _todoDao;
    } else {
      synchronized(this) {
        if(_todoDao == null) {
          _todoDao = new TodoDao_Impl(this);
        }
        return _todoDao;
      }
    }
  }

  @Override
  public NoteAndTodoDao getNoteAndTodoDao() {
    if (_noteAndTodoDao != null) {
      return _noteAndTodoDao;
    } else {
      synchronized(this) {
        if(_noteAndTodoDao == null) {
          _noteAndTodoDao = new NoteAndTodoDao_Impl(this);
        }
        return _noteAndTodoDao;
      }
    }
  }
}
