import numpy as np 
import pandas as pd 
import re
import nltk 
import matplotlib.pyplot as plt
import os

from nltk import WordNetLemmatizer, word_tokenize
from nltk.corpus import stopwords
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import classification_report, confusion_matrix, accuracy_score
nltk.download('punkt')
nltk.download('wordnet')
nltk.download('stopwords')
file_path = r"C:\Users\xinpe\IdeaProjects\scikittest\RateMyProfessor-8c.csv"
messages = pd.read_csv(file_path)
messages['comments'] = messages['comments'].astype(str).str.replace('\\$', 'money', regex=True)
print(messages.head())
lemmatizer = WordNetLemmatizer()
def preprocess_text(text):
    text = re.sub(r'\W', ' ', text)
    text = re.sub(r'\s+[a-zA-Z]\s+', ' ', text)
    text = re.sub(r'\^[a-zA-Z]\s+', ' ', text)
    text = re.sub(r'\s+', ' ', text, flags=re.I)
    text = re.sub(r'^b\s+', '', text)
    text = text.lower()
    text = word_tokenize(text)
    text = [lemmatizer.lemmatize(word) for word in text if word not in stopwords.words('english')]
    return ' '.join(text) if text else 'placeholder'

messages['comments'] = messages['comments'].apply(preprocess_text)

plot_size = plt.rcParams["figure.figsize"]
print(plot_size[0])
print(plot_size[1])
print(messages['comments'].value_counts())
plot_size[0] = 8
plot_size[1] = 6
plt.rcParams["figure.figsize"] = plot_size
messages['student_star'].value_counts().plot(kind='pie', autopct='%1.0f%%')
# plt.show()

features = messages.iloc[:, -1].values
labels = messages.iloc[:, 1].values
print("processign data")
processed_features = []

messages['comments'] = messages['comments'].apply(preprocess_text)

print("stopwords")
vectorizer = TfidfVectorizer (max_features=2500, min_df=5, max_df=0.8, stop_words=stopwords.words('english'))
processed_features = vectorizer.fit_transform(processed_features).toarray()
print("training 2")
X_train, X_test, y_train, y_test = train_test_split(processed_features, labels, test_size=0.2, random_state=0)
print("training 1")
text_classifier = RandomForestClassifier(n_estimators=6, random_state=0)
text_classifier.fit(X_train, y_train)
predictions = text_classifier.predict(X_test)
print(confusion_matrix(y_test,predictions))
print(classification_report(y_test,predictions))
print(accuracy_score(y_test, predictions))