# MOAOnlineClustering
A few MOA stream clustering algorithms are run:
* `CluStream`, and
* `DenStream`

Both take data from a CSV file, and both resulting clusterings are stored in files.


### :grey_exclamation: Important: configuration 
There is a `Config` class, used to get the _paths_ of the folders in which clustering data will be fetched, and results will be stored. Those paths, have to be specified in a **json** file. This is done because many applicatios (developed in different languages) use the same folders. Here, a `config.json` example is shown:

```json
{
    "clusteringResultsPath": "/home/camila/Desktop/TESIS/DATA/clustering_results/",
    "csvDatasetsPath": "/home/camila/Desktop/TESIS/DATA/datasets/",
    "figuresPath": "/home/camila/Desktop/TESIS/DATA/figures/",
    "timeSeriesToyDatasetName": "custom_circumferences_without_k.csv",
    "algoNames": {
	"clustream": "CluStream",
	"denstream": "DenStream"
     }
}
```

**The path to the json file must be specified in the Config class.**
