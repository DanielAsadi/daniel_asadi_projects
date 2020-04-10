import numpy as np
from scipy.stats import norm
import matplotlib.pyplot as plt


def main():
    num_elements = 500
    X = [0] * num_elements
    i = 0

    filepath = 'data/distribution500.txt'
    file = open(filepath, 'r')
    arr = file.readlines()
    for line in arr:
        line = line.split()
        X[i] = float(line[0])
        i += 1

    data = sorted(X)
    print(data)
    mu, std = norm.fit(data)

    # Plot the histogram.
    plt.hist(data, 'auto', density=True, alpha=0.6, color='b')

    # Plot the PDF.
    xmin, xmax = plt.xlim()
    x = np.linspace(xmin, xmax, 500)
    p = norm.pdf(x, mu, std)
    print(mu)
    print(std)
    plt.plot(x, p, 'k', linewidth=2)
    plt.title('# of targets acquired Vs. Frequency of score in 500 trials')
    plt.xlabel('# of targets acquired')
    plt.ylabel('Frequency of score in 500 trials')
    plt.show()


if __name__ == "__main__":
    main()
