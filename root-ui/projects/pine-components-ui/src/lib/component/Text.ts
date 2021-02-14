export class Text {

  public hidden: boolean = false

  private content: string = '';

  constructor(builder: TextBuilder) {
    this.content = builder._content;
    this.hidden = builder._hidden;
  }

  get getContent(): string {
    return this.hidden ? '' : this.content;
  }

  set setContent(value: string) {
    this.content = value;
  }

  public static builder(text: string): TextBuilder {
    return new TextBuilder(text);
  }

  public static of(text: string): Text {
    return Text.builder(text).build();
  }
}

export class TextBuilder {

  _content: string = '';

  _hidden: boolean = false;

  constructor(name: string) {
    this._content = name;
  }

  hidden(value: boolean = false): TextBuilder {
    this._hidden = value;
    return this;
  }

  public build(): Text {
    return new Text(this);
  }
}
