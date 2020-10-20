export class Icon {

  name: string = '';

  hidden: boolean = false

  constructor(builder: IconBuilder) {
    this.name = builder._name;
    this.hidden = builder._hidden;
  }

  public static builder(text: string): IconBuilder {
    return new IconBuilder(text);
  }

  public static iconOf(text: string): Icon {
    return Icon.builder(text).build();
  }
}

export class IconBuilder {

  _name: string = '';

  _hidden: boolean = false;

  constructor(name: string) {
    this._name = name;
  }

  hidden(): IconBuilder {
    this._hidden = true;
    return this;
  }

  public build(): Icon {
    return new Icon(this);
  }
}

export class Text {

  hidden: boolean = false

  constructor(builder: TextBuilder) {
    this._content = builder.content;
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

  content: string = '';

  _hidden: boolean = false;

  constructor(name: string) {
    this.content = name;
  }

  hidden(): TextBuilder {
    this._hidden = true;
    return this;
  }

  public build(): Text {
    return new Text(this);
  }
}

export class Properties {

  text: Text = Text.of('');
  hidden: boolean = false;
  space: string = ' '
  icon: Icon = Icon.iconOf('');

  constructor(builder: PropertiesBuilder) {
    this.text = builder._text;
    this.hidden = builder._hidden;
    this.space = builder._space;
    this.icon = builder._icon;
  }

  public static builder(text: string): PropertiesBuilder {
    return new PropertiesBuilder(text);
  }

}

export class PropertiesBuilder {

  _text: Text = Text.of('');

  _hidden: boolean = false;

  _space: string = ' '

  _icon: Icon = Icon.iconOf('');

  constructor(value: string) {
    this._text = Text.of(value);
  }

  hidden(): PropertiesBuilder {
    this._hidden = true;
    return this;
  }

  hiddenText(): PropertiesBuilder {
    this._text.hidden = true;
    return this;
  }

  space(value: string): PropertiesBuilder {
    this._space = value;
    return this;
  }

  icon(value: Icon): PropertiesBuilder {
    this._icon = value;
    return this;
  }

  public build(): Properties {
    return new Properties(this);
  }
}



