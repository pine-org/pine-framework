for i in application*
do
  NEWNAME="${i/application/bootstrap}"
  mv -- "$i" "$NEWNAME"
done