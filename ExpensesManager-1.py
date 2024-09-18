from Expense import *


class ExpensesManager(object):
    """A class for managing expenses in a dictionary.
    """

    # We do not have an __init__ function and will call the default constructor

    def get_expense(self, expenses, expense_type):
        """Returns the Expense object for the given expense type in the given expenses dictionary.

        Prints a friendly message and returns None if the expense type doesn't exist.

        (Note: Printing a friendly message means that the program should not raise an error or otherwise terminate.
        Simply tell the user that the requested expense type does not exist and continue the program.

        Also note that None is a specific keyword in Python of NoneType. You should not return a string “None” from
        this method.)
        """
        if expense_type in expenses:            #Retrieves an Expense object for a given expense type from the provided expenses dictionary.
            return expenses[expense_type]       #Returns:    - Expense object if the expense_type exists within the expenses dictionary.

        else:
            print("Sorry, that expense type doesn't exist.")
            return None                         #Returns:   - None if the expense_type does not exist in the dictionary.

    def add_expense(self, expenses, expense_type, value):
        """If the expense_type already exists in the given expenses dictionary, add the value to the associated
        Expense object amount.

        Otherwise, create a new entry in the expenses dictionary with the expense_type as the
        key and the value as an Expense object with the given expense_type and value parameters.

        Prints the updated Expense object.

        This method doesn’t return anything.
        """
        # Check if the expense_type already exists in the expenses dictionary
        if expense_type in expenses:
            # If it exists, add the given value to the existing Expense object's amount
            expenses[expense_type].add_amount(value)
        else:
            # If it doesn't exist, create a new Expense object and add it to the dictionary
            expenses[expense_type] = Expense(expense_type, value)
        # Print the updated or newly created Expense object
        print(expenses[expense_type])


    def deduct_expense(self, expenses, expense_type, value):
        """From the given expenses dictionary, get the Expense object associated with the given expense_type and
        deduct the given value from the amount.

        Raises a RuntimeError if the given value is greater than the existing amount of the Expense object. Note: You
        are not supposed to use try/except to catch the RuntimeError you raised. We expect the method to raise a
        RuntimeError if the value is greater than the existing total of the expense type.

        Prints a friendly message if the expense type doesn't exist from the given expenses dictionary. (Note:
        Printing a friendly message means that the program should not raise an error or otherwise terminate. Simply
        tell the user that the requested expense type does not exist and continue the program.)

        Print the updated Expense object if RuntimeError is not raised.

        This method doesn’t return anything.
        """
        # Check if the expense_type exists in the expenses dictionary
        if expense_type not in expenses:
            # Print a friendly message if the expense_type doesn't exist
            print("Sorry, that expense type doesn't exist.")
            return  # Exit the function early
        # Raise an error if the deduction value exceeds the current expense's amount
        if expenses[expense_type].amount < value:
            raise RuntimeError("Trying to deduct more than the total expense.")
        # Deduct the specified value from the expense's amount
        expenses[expense_type].deduct_amount(value)
        # Print the updated Expense object
        print(expenses[expense_type])

    def update_expense(self, expenses, expense_type, value):
        """From the given expenses dictionary, update the Expense object associated with the given expense_type and
        use the given value.

        Prints a friendly message if the expense type doesn't exist.  Note: Printing a friendly message means that
        the program should not raise an error or otherwise terminate. Simply tell the user that the requested expense
        type does not exist and continue the program.

        Prints the updated Expense object if it exists.

        This method doesn’t return anything.
        """
        # Check if the expense_type exists in the expenses dictionary
        if expense_type not in expenses:
            # Print a friendly message if the expense_type doesn't exist
            print("Sorry, that expense type doesn't exist.")
            return  # Exit the function early if expense_type does not exist
        # Update the amount of the specified expense_type to the given value
        expenses[expense_type].amount = value
        # Print the updated Expense object
        print(expenses[expense_type])

    def sort_expenses(self, expenses, sorting):
        """Converts the key:value pairs in the given expenses dictionary to a list of tuples containing the expense
        type and amount of the Expense object (Expense.expense_type, Expense.amount) and sorts based on the given
        sorting argument.

        If the sorting argument is the string ‘expense_type’, sorts the list of tuples based on the expense type
        (e.g. ‘rent’) in ascending alphabetical order of the expense_type, e.g. sorted results: ("coffee", 5.0),
        ("food", 5000.0), ("rent", 1000.0)

        Otherwise, if the sorting argument is ‘amount’, sorts the list of tuples based on the total expense amount
        (e.g. 825.0) in descending order of the expense amount, e.g. sorted results: ("food", 5000.0), ("rent",
        1000.0), ("coffee", 5.0)

        Returns the list of sorted tuples. (Note: If the given sorting argument is not an acceptable value
        (e.g. ‘expense_type’ or 'amount'), this method does nothing except print a friendly message and return None.)
        """
        # Initialize an empty list to store sorted expenses
        sorted_list = []
        # Check if the sorting argument is 'expense_type'
        if sorting == 'expense_type':
            # Sort the expenses dictionary by keys (expense types) in ascending order
            sorted_list = sorted(expenses.items(), key=lambda x: x[0])
        # Check if the sorting argument is 'amount'
        elif sorting == 'amount':
            # Sort the expenses dictionary by values (expense amounts) in descending order
            sorted_list = sorted(expenses.items(), key=lambda x: x[1].amount, reverse=True)
        # Handle invalid sorting arguments
        else:
            # Print a friendly error message for invalid sorting type
            print("Invalid sorting type.")
            # Return None for invalid sorting arguments
            return None
        # Convert sorted items to a list of tuples containing only the expense type and amount
        return [(item[0], item[1].amount) for item in sorted_list]

    def export_expenses(self, expenses, expense_types, file):
        """Exports the Expense objects associated with the given expense_types from the given expenses dictionary to
        the given file.

        Do not append to the file. If the function is called again and the given file already exists, make sure it
        overwrites what was previously in the file instead of appending to it.

        Iterates over the given expenses dictionary, filters based on the given expense types (a list of strings),
        and exports to a file.  Skips any expense type in the given list of expense types that doesn't exist.

        If the expenses argument is the dictionary {"food": Expense("food", 5000.00), "rent": Expense("rent",
        1000.00), "coffee": Expense("coffee", 5.00), "clothes": Expense("clothes", 58.92)} and the expense_types
        argument is the list of strings [‘coffee', 'clothes', 'rent’], exports a file containing: coffee: 5.00
        clothes: 58.92 rent: 1000.00

        If the expenses argument is the dictionary {"food": Expense("food", 5000.00), "rent": Expense("rent",
        1000.00), "coffee": Expense("coffee", 5.00), "clothes": Expense("clothes", 58.92)} and the expense_types
        argument is the list of strings [‘coffee', 'clothes', 'sports’], exports a file containing: coffee: 5.00
        clothes: 58.92

        Note, the specified expense type 'sports' does not exist in the expenses dictionary, so it is ignored.

        If an item is duplicated in the given expense types, don’t worry about it, just export the data as is. You
        should not deduplicate the expense types.

        Note: Each purchase should be written on its own line in the output file for example if it were the later example
        that we gave you above, the output file would look like the below:
        coffee: 12.40
        clothes: 45.00

        This method doesn’t return anything.
        """
        """Exports the expenses to a given file."""
        # Open the file in write mode, which will also overwrite if the file already exists
        with open(file, 'w') as f:
            # Iterate through each expense type provided in the expense_types list
            for e_type in expense_types:
                # Check if the current expense type exists in the expenses dictionary
                if e_type in expenses:
                    # Write the expense type and its amount to the file in the specified format
                    f.write(f"{e_type}: {expenses[e_type].amount:.2f}\n")
