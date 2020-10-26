package softuni.gamestoredemo2.domain.dtos;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GameDetailsDto {
    private String title;
    private BigDecimal price;
    private String description;
    private LocalDate releaseDate;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Title: ").append(this.getTitle()).append(System.lineSeparator())
                .append("Price: ").append(this.getPrice()).append(System.lineSeparator())
                .append("Description: ").append(this.getDescription()).append(System.lineSeparator())
                .append("Release date: ").append(this.getReleaseDate());

        return sb.toString();
    }
}
