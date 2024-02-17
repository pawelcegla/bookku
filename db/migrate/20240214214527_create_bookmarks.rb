class CreateBookmarks < ActiveRecord::Migration[7.1]
  def change
    create_table :bookmarks do |t|
      t.string :bookmark_id
      t.string :target

      t.timestamps
    end
    add_index :bookmarks, :bookmark_id
  end
end
