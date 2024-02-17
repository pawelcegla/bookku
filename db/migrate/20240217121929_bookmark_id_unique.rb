class BookmarkIdUnique < ActiveRecord::Migration[7.1]
  def change
    remove_index :bookmarks, :bookmark_id
    add_index :bookmarks, :bookmark_id, unique: true
  end
end
