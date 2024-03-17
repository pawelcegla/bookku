class Bookmark < ApplicationRecord
    validates :bookmark_id, presence: true, uniqueness: true
    validates :target, presence: true
end
