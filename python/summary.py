import numpy as np
import pandas as pd
import sys

n = len(sys.argv)
df = pd.read_csv(sys.argv[1])
df.describe().to_csv('something.csv')

df.isna().sum().to_csv('nacounts.csv')
df.corr().to_csv('corr.csv')