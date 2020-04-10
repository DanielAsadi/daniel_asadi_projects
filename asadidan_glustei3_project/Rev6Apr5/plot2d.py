import matplotlib.pyplot as plt


def main():
    num_elements = 11
    X = [0] * num_elements
    Y = [0] * num_elements
    i = 0

    filepath = 'data/cycle.txt'
    file = open(filepath, 'r')
    arr = file.readlines()
    for line in arr:
        line = line.split()
        X[i] = float(line[0])
        Y[i] = float(line[1])
        i += 1

    print(X)
    print(Y)
    # plot
    plt.plot(X, Y)

    # change axes ranges
    # plt.xlim(0, 100)
    # plt.ylim(0, 40)

    # add title
    plt.title('CYCLE_ALLOWANCES Vs. # of targets acquired')

    # add x and y labels
    plt.xlabel('CYCLE_ALLOWANCES')
    plt.ylabel('# of targets acquired')

    # show plot
    plt.show()


if __name__ == "__main__":
    main()
