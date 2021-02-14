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

  hidden(value: boolean = false): IconBuilder {
    this._hidden = value;
    return this;
  }

  public build(): Icon {
    return new Icon(this);
  }
}
