package sharma.vardan.me.grabonrenttest.data.model;

final public class Product {
  public final String text;
  public final String image;

  public Product(String text, String image) {
    this.text = text;
    this.image = image;
  }

  public String getText() {
    return text;
  }

  public String getImage() {
    return image;
  }

  @Override public String toString() {
    return "Product{" +
        "text='" + text + '\'' +
        ", image='" + image + '\'' +
        '}';
  }
}
