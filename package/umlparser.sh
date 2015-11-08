#Script to execute uml parser

EXPECTED_ARGS=2

if [ $# ==  $EXPECTED_ARGS ]; then
	java -jar  umlparser.jar $1 $2
else
	echo "Usage :  'basename $0' sourcepath imageName "
	exit 1
fi
