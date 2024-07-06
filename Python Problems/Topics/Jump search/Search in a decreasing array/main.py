def binary_search_desc(arr, target):
    left, right = 0, len(arr) - 1

    while left <= right:
        mid = (left + right) // 2
        if arr[mid] == target:
            return mid  # Return the index directly (0-based index)
        elif arr[mid] < target:
            right = mid - 1  # Adjust right boundary for descending order
        else:
            left = mid + 1   # Adjust left boundary for descending order

    return -1


def main():
    with open('data/dataset/input.txt', 'r') as f:
        lines = f.read().splitlines()

    desc_array = list(map(int, lines[0].split()))
    to_find = list(map(int, lines[1].split()))

    results = []
    for num in to_find:
        index = binary_search_desc(desc_array, num)
        results.append(index)

    print(" ".join(map(str, results)))

if __name__ == "__main__":
    main()
