for i in {1..5}
do
     gnome-terminal -e "sh -c \"java -jar /home/ahmed/Desktop/Project/Server/Server/out/artifacts/Server_jar/Server.jar $i 5; sleep 10; exec bash\""
    # java -jar /home/ahmed/Desktop/Project/Server/Server/out/artifacts/Server_jar/Server.jar $i 5 
done
