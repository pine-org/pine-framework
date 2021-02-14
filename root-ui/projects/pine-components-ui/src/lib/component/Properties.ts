import {Icon} from "./Icon";
import {Text} from "./Text";

export class Properties {
  title: Text = Text.of('');
  field: Text = Text.of('');
  type: Text = Text.of('');
  hidden: boolean = false;
  separator: string = ' '
  icon: Icon = Icon.iconOf('');

  constructor(builder: PropertiesBuilder) {
    this.title = builder._title;
    this.field = builder._field
    this.type = builder._type
    this.hidden = builder._hidden;
    this.separator = builder._separator;
    this.icon = builder._icon;
  }

  public static builder(text: Text): PropertiesBuilder {
    return new PropertiesBuilder(text);
  }

}

export class PropertiesBuilder {

  _title: Text = Text.of('');

  _field: Text = Text.of('');

  _type: Text = Text.of('');

  _hidden: boolean = false;

  _separator: string = ' '

  _icon: Icon = Icon.iconOf('');

  constructor(text: Text) {
    this._title = text;
  }

  hidden(value: boolean = true): PropertiesBuilder {
    this._hidden = value;
    return this;
  }

  field(value: string): PropertiesBuilder {
    this._field = Text.of(value);
    return this;
  }

  type(value: string): PropertiesBuilder {
    this._type = Text.of(value);
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



