require 'test_helper'

class BookmarkTest < ActiveSupport::TestCase
  test 'should not save a bookmark without an id & a target' do
    b = Bookmark.new
    assert_not b.save
  end
  test 'should not save a bookmark with an id that already exists' do
    b = Bookmark.new(bookmark_id: 'ex', target: 'https://example.org')
    assert_not b.save
  end
end
