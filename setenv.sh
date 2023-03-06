values=$(curl --request GET  --url 'https://api.doppler.com/v3/configs/config/secrets/download?format=json'  --header "Authorization: Bearer $1")

for s in $(echo $values | jq -r "to_entries|map(\"\(.key)=\(.value|tostring)\")|.[]" ); do 
    echo $s	 
    export $s
done
~    
