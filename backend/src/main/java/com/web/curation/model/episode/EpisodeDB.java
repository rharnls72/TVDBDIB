package com.web.curation.model.episode;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeDB {
    private int eno;
    private int pno;
    private int season;
    private int episode;
	private int share_num;

    // Additional attribute
	private int like_num;
	private boolean press_like;
	private int reply_num;
	private String reply_content;
	private int dibs_num;
	private boolean press_dibs;

    public int getEno() {
        return this.eno;
    }

    public void setEno(int eno) {
        this.eno = eno;
    }

    public int getPno() {
        return this.pno;
    }

    public void setPno(int pno) {
        this.pno = pno;
    }

    public int getSeason() {
        return this.season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return this.episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public int getShare_num() {
        return this.share_num;
    }

    public void setShare_num(int share_num) {
        this.share_num = share_num;
    }


	public int getLike_num() {
		return this.like_num;
	}

	public void setLike_num(int like_num) {
		this.like_num = like_num;
	}

	public boolean isPress_like() {
		return this.press_like;
	}

	public void setPress_like(boolean press_like) {
		this.press_like = press_like;
	}

	public int getReply_num() {
		return this.reply_num;
	}

	public void setReply_num(int reply_num) {
		this.reply_num = reply_num;
	}

	public String getReply_content() {
		return this.reply_content;
	}

	public void setReply_content(String reply_content) {
		this.reply_content = reply_content;
	}

	public int getDibs_num() {
		return this.dibs_num;
	}

	public void setDibs_num(int dibs_num) {
		this.dibs_num = dibs_num;
	}

	public boolean isPress_dibs() {
		return this.press_dibs;
	}

	public void setPress_dibs(boolean press_dibs) {
		this.press_dibs = press_dibs;
	}
}

