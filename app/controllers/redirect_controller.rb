class RedirectController < ApplicationController
  def location
    b = Bookmark.find_by(bookmark_id: params[:bookmark_id])
    if b
      redirect_to b.target, status: :temporary_redirect, allow_other_host: true
    else
      render body: ':not_found', status: :not_found
    end
  end
end
