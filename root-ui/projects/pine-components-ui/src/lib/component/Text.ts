export class Text {

  public hidden: boolean = false

  constructor(builder: TextBuilder) {
    this._content = builder._content;
    this.hidden = builder._hidden;
  }

  private _content: string = '';

  get content(): string {
    return this.hidden ? '' : this._content;
  }

  set content(value: string) {
    this._content = value;
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
