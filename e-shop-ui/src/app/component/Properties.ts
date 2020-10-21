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

  title: Text = Text.of('');
  field: Text = Text.of('');
  hidden: boolean = false;
  separator: string = ' '
  icon: Icon = Icon.iconOf('');

  constructor(builder: PropertiesBuilder) {
    this.title = builder._title;
    this.field = builder._field
    this.hidden = builder._hidden;
    this.separator = builder._separator;
    this.icon = builder._icon;
  }

  public static builder(text: string): PropertiesBuilder {
    return new PropertiesBuilder(text);
  }

}

export class PropertiesBuilder {

  _title: Text = Text.of('');

  _field: Text = Text.of('');

  _hidden: boolean = false;

  _separator: string = ' '

  _icon: Icon = Icon.iconOf('');

  constructor(value: string) {
    this._title = Text.of(value);
  }

  hidden(): PropertiesBuilder {
    this._hidden = true;
    return this;
  }

  hiddenTitle(): PropertiesBuilder {
    this._title.hidden = true;
    return this;
  }

  field(value: string): PropertiesBuilder {
    this._field = Text.of(value);
    return this;
  }

  separator(value: string): PropertiesBuilder {
    this._separator = value;
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



